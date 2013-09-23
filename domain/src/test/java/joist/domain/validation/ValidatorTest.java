package joist.domain.validation;

import joist.domain.orm.DummyDomainObject;

import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {

  @Test
  public void validateWorksWithValidDomainObject() {
    Validator v = new Validator();
    v.enqueue(new DummyDomainObject());
    v.validate();
  }

  @Test
  public void validateFailsIfUpdateDerivedValuesWasNotCalled() {
    Validator v = new Validator();
    v.enqueue(new DummyDomainObject() {
      @Override
      public void updateDerivedValues() {
        // do not call super.updateDerivedValues
      }
    });
    try {
      v.validate();
      Assert.fail();
    } catch (IllegalStateException ise) {
      Assert.assertEquals("[null] updateDerivedValues forgot to call super.updateDerivedValues", ise.getMessage());
    }
  }

  @Test
  public void validateWorksIfUpdateDerivedValuesWasCalled() {
    Validator v = new Validator();
    v.enqueue(new DummyDomainObject() {
      @Override
      public void updateDerivedValues() {
        super.updateDerivedValues();
      }
    });
    // no exception means it passed
    v.validate();
  }

}
