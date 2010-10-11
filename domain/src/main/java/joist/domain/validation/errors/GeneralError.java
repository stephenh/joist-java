package joist.domain.validation.errors;

public class GeneralError implements ValidationError {

  private final String message;

  public GeneralError(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public String toString() {
    return this.getMessage();
  }

}
