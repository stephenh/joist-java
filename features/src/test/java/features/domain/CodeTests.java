package features.domain;

import joist.domain.ValidationAssert;

import org.junit.Assert;
import org.junit.Test;

public class CodeTests extends AbstractFeaturesTest {

  @Test
  public void testDefaults() {
    CodeADomainObject d = new CodeADomainObject();

    Assert.assertEquals("the default name", d.getName());
    Assert.assertEquals(false, d.getChanged().hasName());

    Assert.assertEquals(CodeAColor.BLUE, d.getCodeAColor());
    Assert.assertEquals(false, d.getChanged().hasCodeAColor());
  }

  @Test
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

  @Test
  public void testRequiredDoesNotNpe() {
    CodeADomainObject d = new CodeADomainObject();
    d.setName("name");
    d.setCodeASize(CodeASize.ONE);
    d.setCodeAColor(null);
    ValidationAssert.assertErrors(d, "Code AColor is required");
  }

  @Test
  public void testIsMethods() {
    CodeADomainObject d = new CodeADomainObject();
    d.setCodeASize(CodeASize.ONE);
    Assert.assertTrue(d.isOne());
    Assert.assertFalse(d.isTwo());

    d.setCodeASize(CodeASize.TWO);
    Assert.assertTrue(d.isTwo());
    Assert.assertFalse(d.isOne());
  }

  @Test
  public void testFindByMethods() {
    CodeADomainObject d = new CodeADomainObject();
    d.setCodeASize(CodeASize.ONE);
    this.commitAndReOpen();

    Assert.assertEquals(1, CodeADomainObject.queries.findByCodeASize(CodeASize.ONE).size());
    Assert.assertEquals(0, CodeADomainObject.queries.findByCodeASize(CodeASize.TWO).size());

    Assert.assertEquals(1, CodeADomainObject.queries.findIdsByCodeASize(CodeASize.ONE).size());
    Assert.assertEquals(0, CodeADomainObject.queries.findIdsByCodeASize(CodeASize.TWO).size());
  }

}
