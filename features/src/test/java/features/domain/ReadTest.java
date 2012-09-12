package features.domain;

import static features.domain.builders.Builders.aParent;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import joist.domain.uow.BlockWithReturn;
import joist.domain.uow.UoW;

import org.junit.Test;

import features.domain.builders.ParentBuilder;

public class ReadTest extends AbstractFeaturesTest {

  @Test
  public void testRead() {
    ParentBuilder p = aParent().defaults();
    this.commitAndReOpen();
    UoW.close();
    String name = UoW.read(repo, new BlockWithReturn<String>() {
      public String go() {
        return Parent.queries.find(1).getName();
      }
    });
    assertThat(name, is(p.name()));
  }

  @Test
  public void testReadIsReadOnly() {
    aParent().defaults();
    this.commitAndReOpen();
    UoW.close();
    try {
      UoW.read(repo, new BlockWithReturn<String>() {
        public String go() {
          Parent p = Parent.queries.find(1);
          p.setName("asdf");
          return p.getName();
        }
      });
      fail();
    } catch (RuntimeException re) {
      assertThat(re.getMessage(), is("Cannot modify Parent[1] while reading"));
    }
  }

}
