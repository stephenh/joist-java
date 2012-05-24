package joist.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Inflector {

  private static final Pattern lower = Pattern.compile("[a-z]");
  private static final Pattern numeric = Pattern.compile("[0-9]");

  private Inflector() {
  }

  public static String underscore(String camelCased) {
    List<String> parts = Inflector.split(camelCased);
    for (int i = 0; i < parts.size(); i++) {
      parts.set(i, parts.get(i).toLowerCase());
    }
    return Join.underscore(parts);
  }

  public static String camelize(String s) {
    StringBuffer name = new StringBuffer();
    for (String part : s.split(" |_")) {
      name.append(Inflector.capitalize(part));
    }
    return name.toString();
  }

  public static String humanize(String camelCased) {
    List<String> parts = Inflector.split(camelCased);
    for (int i = 0; i < parts.size(); i++) {
      parts.set(i, Inflector.capitalize(parts.get(i).toLowerCase()));
    }
    return Join.space(parts);
  }

  public static String capitalize(String str) {
    if (str == null || str.length() == 0) {
      return str;
    }
    return Character.toTitleCase(str.charAt(0)) + str.substring(1);
  }

  public static String uncapitalize(String str) {
    if (str == null || str.length() == 0) {
      return str;
    }
    return Character.toLowerCase(str.charAt(0)) + str.substring(1);
  }

  /** Splits word into groups based on where underscores and case changes are. */
  private static List<String> split(String word) {
    List<String> parts = new ArrayList<String>();
    boolean wasLower = false;
    boolean wasNumeric = false;
    int at = 0;
    int length = word.length();
    for (int i = 0; i < length; i++) {
      String c = word.substring(i, i + 1);
      boolean isLower = Inflector.lower.matcher(c).matches();
      boolean isNumeric = Inflector.numeric.matcher(c).matches();
      if ("_".equals(c)) {
        parts.add(word.substring(at, i));
        at = i + 1;
      } else if (wasLower && !isLower) {
        parts.add(word.substring(at, i)); // add last chunk
        at = i;
      } else if (wasNumeric && !isNumeric) {
        parts.add(word.substring(at, i)); // add last chunk
        at = i;
      } else if (!wasNumeric && isNumeric) {
        parts.add(word.substring(at, i)); // add last chunk
        at = i;
      }
      wasLower = isLower;
      wasNumeric = isNumeric;
    }
    if (at != length) {
      parts.add(word.substring(at));
    }
    return parts;
  }

}
