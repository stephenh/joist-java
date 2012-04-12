package features.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ManyToManyBTest extends AbstractFeaturesTest {

  @Test
  public void testTwoParents() {
    // foo "owns" the bars
    ManyToManyBFoo foo = new ManyToManyBFoo();
    ManyToManyBBar bar1 = new ManyToManyBBar();
    ManyToManyBBar bar2 = new ManyToManyBBar();

    foo.addOwned(bar1);
    foo.addOwned(bar2);

    assertThat(bar1.getOwnerManyToManyBFoos().size(), is(1));
    assertThat(bar2.getOwnerManyToManyBFoos().size(), is(1));

    // add me as the owner
    // foo.addOwnerManyToManyBFooToBar()

    // add me as owned
    // bar2.addOwnedManyToManyBFooToBar()
  }
}
