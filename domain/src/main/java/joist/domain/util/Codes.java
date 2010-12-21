package joist.domain.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;

import joist.domain.Code;

public class Codes {

  private Codes() {
  }

  public static <T extends Code> T[] valuesByCode(T[] codes) {
    T[] copy = Arrays.copyOf(codes, codes.length);
    Arrays.sort(copy, new Comparator<T>() {
      public int compare(T c1, T c2) {
        return c1.getCode().compareTo(c2.getCode());
      }
    });
    return copy;
  }

  public static <T extends Code> T[] valuesByName(T[] codes) {
    T[] copy = Arrays.copyOf(codes, codes.length);
    Arrays.sort(copy, new Comparator<T>() {
      public int compare(T c1, T c2) {
        return c1.getName().compareTo(c2.getName());
      }
    });
    return copy;
  }

  public static <T extends Enum<T>> T fromLong(Class<T> codeClass, long i) {
    for (Enum<T> value : EnumSet.allOf(codeClass)) {
      if (((Code) value).getId().longValue() == i) {
        return (T) value;
      }
    }
    return null;
  }

  public static <T extends Code> T fromLong(T[] codes, long i) {
    for (T code : codes) {
      if (code.getId().longValue() == i) {
        return code;
      }
    }
    return null;
  }

  public static <T extends Code> T fromCode(T[] codes, String code) {
    for (T c : codes) {
      if (c.getCode().equals(code)) {
        return c;
      }
    }
    return null;
  }

}
