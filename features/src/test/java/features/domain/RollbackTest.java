package features.domain;

import static features.domain.builders.Builders.aParent;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import joist.domain.uow.Block;
import joist.domain.uow.UoW;

import org.junit.Test;

import features.domain.builders.ParentBuilder;

public class RollbackTest extends AbstractFeaturesTest {

  @Test
  public void testCommitFailsIfRolledBack() {
    // given an object
    ParentBuilder p = aParent().defaults();
    this.commitAndReOpen();
    // when we make an initial change
    p.name("newname");
    // and roll it back
    UoW.rollback();
    // then we can't commit
    try {
      UoW.commit();
      fail();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage(), is("UoW has been rolled back"));
    }
    // and the name didn't change
    this.commitAndReOpen();
    assertThat(p.name(), is("name"));
  }

  @Test
  public void testFlushFailsIfRolledBack() {
    // given an object
    ParentBuilder p = aParent().defaults();
    this.commitAndReOpen();
    // when we make an initial change
    p.name("newname");
    // and roll it back
    UoW.rollback();
    // then we can't flush
    try {
      UoW.flush();
      fail();
    } catch (IllegalStateException ise) {
      assertThat(ise.getMessage(), is("UoW has been rolled back"));
    }
    // and the name didn't change
    this.commitAndReOpen();
    assertThat(p.name(), is("name"));
  }

  @Test
  public void testBlockWhenRolledBack() {
    // given an object
    final ParentBuilder p = aParent().defaults();
    this.commitAndReOpen();
    // when we make a change in a block
    UoW.close();
    UoW.go(repo, null, new Block() {
      public void go() {
        p.name("newname");
        // and roll it back
        UoW.rollback();
      }
    });
    UoW.open(repo, null);
    // and the name didn't change
    assertThat(p.name(), is("name"));
  }

}
