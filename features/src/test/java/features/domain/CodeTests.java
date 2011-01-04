package features.domain;

import joist.domain.ValidationAssert;
import junit.framework.Assert;

public class CodeTests extends AbstractFeaturesTest {

  public void testDefaults() {
    CodeADomainObject d = new CodeADomainObject();

    Assert.assertEquals("the default name", d.getName());
    Assert.assertEquals(false, d.getChanged().hasName());

    Assert.assertEquals(CodeAColor.BLUE, d.getCodeAColor());
    Assert.assertEquals(false, d.getChanged().hasCodeAColor());
  }

  public void testSave() {
    CodeADomainObject d = new CodeADomainObject();
    d.setCodeAColor(CodeAColor.BLUE);
    d.setCodeASize(CodeASize.ONE);
    d.setName("foo");
    this.commitAndReOpen();

    d = CodeADomainObject.queries.find(1);
    Assert.assertEquals(CodeAColor.BLUE, d.getCodeAColor());
    Assert.assertEquals(CodeASize.ONE, d.getCodeASize());
  }

  public void testRequiredDoesNotNpe() {
    CodeADomainObject d = new CodeADomainObject();
    d.setName("name");
    d.setCodeASize(CodeASize.ONE);
    d.setCodeAColor(null);
    ValidationAssert.assertErrors(d, "Code AColor is required");
  }

  public void testIsMethods() {
    CodeADomainObject d = new CodeADomainObject();
    d.setCodeASize(CodeASize.ONE);
    Assert.assertTrue(d.isOne());
    Assert.assertFalse(d.isTwo());

    d.setCodeASize(CodeASize.TWO);
    Assert.assertTrue(d.isTwo());
    Assert.assertFalse(d.isOne());
  }

}
