package joist.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Utility methods for wrapping strings with quotes/backquotes/etc.
 * 
 *  Does not do any escaping of the wrapped strings.
 */
public class Wrap {

  public static String wrap(String input, String token) {
    return token + input + token;
  }

  public static Collection<String> wrap(Collection<String> input, String token) {
    List<String> wrapped = new ArrayList<String>();
    for (String i : input) {
      wrapped.add(Wrap.wrap(i, token));
    }
    return wrapped;
  }

  public static String ticks(String input) {
    return Wrap.wrap(input, "'");
  }

  public static Collection<String> ticks(String... input) {
    return Wrap.wrap(Arrays.asList(input), "'");
  }

  public static Collection<String> ticks(List<String> input) {
    return Wrap.wrap(input, "'");
  }

  public static String backquotes(String input) {
    return Wrap.wrap(input, "`");
  }

  public static Collection<String> backquotes(String... input) {
    return Wrap.wrap(Arrays.asList(input), "`");
  }

  public static Collection<String> backquotes(Collection<String> input) {
    return Wrap.wrap(input, "`");
  }

  public static String quotes(String input) {
    return Wrap.wrap(input, "\"");
  }

  public static List<String> quotes(String... input) {
    return new ArrayList<String>(Wrap.wrap(Arrays.asList(input), "\""));
  }

  public static List<String> quotes(List<String> input) {
    return new ArrayList<String>(Wrap.wrap(input, "\""));
  }

}
