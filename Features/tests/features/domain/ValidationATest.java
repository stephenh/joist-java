package features.domain;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.validation.ValidationException;
import org.exigencecorp.domainobjects.validation.errors.PropertyError;

public class ValidationATest extends AbstractFeaturesTest {

    public void testNotNull() {
        ValidationAFoo foo = new ValidationAFoo();
        foo.setId(1);
        try {
            this.commitAndReOpen();
            Assert.fail();
        } catch (ValidationException ve) {
            Assert.assertEquals("Name is required", ve.getValidationErrorMessages().get(0));
        }

        foo.setName("bar");
        try {
            this.commitAndReOpen();
            Assert.fail();
        } catch (ValidationException ve) {
            Assert.assertEquals("Name must not be bar", ve.getValidationErrorMessages().get(0));
            Assert.assertEquals("Name must not be bar (on ValidationAFoo[1] bar)", ve.getValidationErrors().get(0).toString());
            Assert.assertEquals(foo, ((PropertyError) ve.getValidationErrors().get(0)).getParent());
        }
    }

    public void testMaxLength() {
        String as = StringUtils.repeat("a", 100);

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

}
