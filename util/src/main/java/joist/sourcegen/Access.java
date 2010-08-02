package joist.sourcegen;

public enum Access {

  PUBLIC("public "), PACKAGE(""), PROTECTED("protected "), PRIVATE("private ");

  private String asPrefix;

  private Access(String asPrefix) {
    this.asPrefix = asPrefix;
  }

  public String asPrefix() {
    return this.asPrefix;
  }

}
