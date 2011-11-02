package features.domain;

import joist.domain.validation.ValidationException;
import joist.domain.validation.errors.ObjectError;
import joist.domain.validation.errors.PropertyError;

import org.junit.Assert;
import org.junit.Test;

public class ValidationATest extends AbstractFeaturesTest {

  @Test
  public void testNotNull() {
    ValidationAFoo foo = new ValidationAFoo();
    foo.setId(1l);
    try {
      this.commitAndReOpen();
      Assert.fail();
    } catch (ValidationException ve) {
      Assert.assertEquals("Name is required", ve.getValidationErrorMessages().get(0));
    }
  }

  @Test
  public void testMaxLength() {
    String as = "";
    for (int i = 0; i < 100; i++) {
      as += "a";
    }

    ValidationAFoo foo = new ValidationAFoo();
    foo.setName(as + "a");
    try {
      this.commitAndReOpen();
      Assert.fail();
    } catch (ValidationException ve) {
      Assert.assertEquals("Name must be no more than 100 characters", ve.getValidationErrorMessages().get(0));
    }

    foo.setName(as);
    this.commitAndReOpen();
  }

  @Test
  public void testCustomPropertyError() {
    ValidationAFoo foo = new ValidationAFoo();
    foo.setName("bar");
    try {
      this.commitAndReOpen();
      Assert.fail();
    } catch (ValidationException ve) {
      Assert.assertEquals("Name must not be bar", ve.getValidationErrorMessages().get(0));
      Assert.assertEquals("Name must not be bar - ValidationAFoo[null]", ve.getValidationErrors().get(0).toString());
      Assert.assertEquals(foo, ((PropertyError) ve.getValidationErrors().get(0)).getInstance());
    }
  }

  @Test
  public void testCustomObjectError() {
    ValidationAFoo foo = new ValidationAFoo();
    foo.setName("baz");
    try {
      this.commitAndReOpen();
      Assert.fail();
    } catch (ValidationException ve) {
      Assert.assertEquals("is all messed up", ve.getValidationErrorMessages().get(0));
      Assert.assertEquals("is all messed up - ValidationAFoo[null]", ve.getValidationErrors().get(0).toString());
      Assert.assertEquals(foo, ((ObjectError) ve.getValidationErrors().get(0)).getInstance());
    }
  }

}
