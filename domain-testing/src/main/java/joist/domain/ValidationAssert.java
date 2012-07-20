package joist.domain;

import java.util.ArrayList;
import java.util.List;

import joist.domain.builders.AbstractBuilder;
import joist.domain.validation.errors.ValidationError;
import joist.util.Join;

import org.junit.Assert;

public class ValidationAssert {

  public static void assertValid(AbstractBuilder<?> builder) {
    assertValid(builder.get());
  }

  public static void assertValid(DomainObject instance) {
    List<ValidationError> errors = instance.validate();
    Assert.assertEquals("", Join.lines(ValidationAssert.toMessages(errors)));
  }

  public static void assertErrors(AbstractBuilder<?> builder, String... messages) {
    assertErrors(builder.get(), messages);
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
