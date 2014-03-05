package features.domain;

import org.junit.Test;

public class ParentHTest {

  @Test
  public void testNameOfTheOneToManyProperty() {
    ParentH p = new ParentH();
    // child has a column called "parent_id" instead of "parent_h_id", so before a tweak
    // to codegen, this was coming out as p.getParentChildHs() when really it can just
    // be getChildHs() without the "Parent" prefix.
    p.getChildHs();
  }

}
