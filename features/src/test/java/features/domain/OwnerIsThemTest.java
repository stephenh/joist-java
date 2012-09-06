package features.domain;

import static features.domain.builders.Builders.aChildG;
import static features.domain.builders.Builders.aParentG;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import features.domain.builders.ParentGBuilder;

public class OwnerIsThemTest extends AbstractFeaturesTest {

  @Test
  public void testSaveWithBothParentsSet() {
    aChildG().parentOne(aParentG().defaults()).parentTwo(aParentG().defaults()).defaults();
    this.commitAndReOpen();
    assertThat(ChildG.queries.count(), is(1l));
    assertThat(ParentG.queries.count(), is(2l));
  }

  @Test
  public void testDeleteWithBothParentsSet() {
    ParentGBuilder one = aParentG().defaults();
    ParentGBuilder two = aParentG().defaults();
    aChildG().parentOne(one).parentTwo(two).defaults();
    this.commitAndReOpen();
    ParentG.queries.delete(one.get());
    this.commitAndReOpen();
    assertThat(ChildG.queries.count(), is(0l));
    assertThat(ParentG.queries.count(), is(1l));
  }

}
