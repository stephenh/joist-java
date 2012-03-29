package features.domain;

import static features.domain.builders.Builders.aChild;
import static features.domain.builders.Builders.aParent;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Test;

import features.domain.builders.ChildBuilder;
import features.domain.builders.ParentBuilder;

public class BuildersTest extends AbstractFeaturesTest {

  @Test
  public void testEquality() {
    Parent p = new Parent();
    ParentBuilder b1 = new ParentBuilder(p);
    ParentBuilder b2 = new ParentBuilder(p);
    Assert.assertEquals(b1, b2);
  }

  @Test
  public void testAddedFunctionalityToRegularMethod() {
    ChildBuilder c = aChild().parent(new Parent());
    assertThat(c.name(), is("foo"));
  }

  @Test
  public void testAddedFunctionalityToBuilderMethod() {
    ChildBuilder c = aChild().parent(aParent());
    assertThat(c.name(), is("foo"));
  }

  @Test
  public void testAddedFunctionalityToWithMethod() {
    ChildBuilder c = aChild().with(aParent());
    assertThat(c.name(), is("foo"));
  }
}
