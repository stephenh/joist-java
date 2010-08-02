package joist.domain;

public class Requirements {

  public static final Requirement selectUniqueWithTooManyFails = new Requirement();
  public static final Requirement selectUniqueWithNoResultsFails = new Requirement();
  public static final Requirement rulesCanBeRegex = new Requirement();
  public static final Requirement selectLimitAndOffset = new Requirement();

  public static class Requirement {
    public void fulfills() {
    }

    public void tests() {
    }
  }

}
