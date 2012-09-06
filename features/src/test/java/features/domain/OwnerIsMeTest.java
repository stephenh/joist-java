package features.domain;

import static features.domain.builders.Builders.aChildF;
import static features.domain.builders.Builders.aParentF;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import features.domain.builders.ParentFBuilder;

public class OwnerIsMeTest extends AbstractFeaturesTest {

  @Test
  public void testSaveWithBothChildrenSet() {
    aParentF().childOne(aChildF().defaults()).childTwo(aChildF().defaults()).defaults();
    this.commitAndReOpen();
    assertThat(ParentF.queries.count(), is(1l));
    assertThat(ChildF.queries.count(), is(2l));
  }

  @Test
  public void testDeleteWithBothChildrenSet() {
    ParentFBuilder f = aParentF().childOne(aChildF().defaults()).childTwo(aChildF().defaults()).defaults();
    this.commitAndReOpen();
    ParentF.queries.delete(f.get());
    this.commitAndReOpen();
    assertThat(ParentF.queries.count(), is(0l));
    assertThat(ChildF.queries.count(), is(0l));
  }

  @Test
  public void testDeleteWithNullableChildUnset() {
    ParentFBuilder f = aParentF().childOne(aChildF().defaults()).defaults();
    this.commitAndReOpen();
    ParentF.queries.delete(f.get());
    this.commitAndReOpen();
    assertThat(ParentF.queries.count(), is(0l));
    assertThat(ChildF.queries.count(), is(0l));
  }

}
