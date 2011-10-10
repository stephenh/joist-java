package joist.sourcegen;

public class GSettings {

  private static String defaultIndentation = System.getProperty("joist.sourcegen.defaultIndentation", "    ");

  public static void setDefaultIndentation(String value) {
    defaultIndentation = value;
  }

  public static String getDefaultIndentation() {
    return defaultIndentation;
  }

}
