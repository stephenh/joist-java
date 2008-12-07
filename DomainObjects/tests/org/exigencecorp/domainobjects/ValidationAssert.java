package org.exigencecorp.domainobjects;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.exigencecorp.domainobjects.validation.errors.ValidationError;
import org.exigencecorp.util.Join;

public class ValidationAssert {

    public static void assertValid(DomainObject instance) {
        List<ValidationError> errors = instance.validate();
        Assert.assertEquals("", Join.lines(ValidationAssert.toMessages(errors)));
    }

    public static void assertErrors(DomainObject instance, String... messages) {
        List<ValidationError> errors = instance.validate();
        Assert.assertEquals(Join.lines(messages), Join.lines(ValidationAssert.toMessages(errors)));
    }

    private static List<String> toMessages(List<ValidationError> errors) {
        List<String> messages = new ArrayList<String>();
        for (ValidationError error : errors) {
            messages.add(error.getMessage());
        }
        return messages;
    }
}
