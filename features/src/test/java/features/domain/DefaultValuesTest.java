package features.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefaultValuesTest extends AbstractFeaturesTest {

  @Test
  public void testDefaultValues() {
    ValuesA v = new ValuesA();
    this.assertDefaults(v);
    v.setName("name");
    this.commitAndReOpen();

    v = this.reload(v);
    this.assertDefaults(v);
  }

  private void assertDefaults(ValuesA v) {
    assertEquals("a", v.getA());
    assertEquals("b", v.getB());
    assertEquals(1, v.getI().intValue());
    assertEquals(2, v.getJ().intValue());
  }

  @Test
  public void testCanBeOverwritten() {
    ValuesA v = new ValuesA();
    v.setName("name");
    v.setA("not a");
    v.setI(999);
    this.assertSetValues(v);
    this.commitAndReOpen();

    v = this.reload(v);
    this.assertSetValues(v);
  }

  private void assertSetValues(ValuesA v) {
    assertEquals("not a", v.getA());
    assertEquals(999, v.getI().intValue());
  }
}
