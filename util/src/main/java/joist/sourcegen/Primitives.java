package joist.sourcegen;

import java.util.HashMap;
import java.util.Map;

public class Primitives {

  private static Map<String, String> primitives = new HashMap<String, String>();
  private static Map<String, String> defaults = new HashMap<String, String>();

  static {
    primitives.put("byte", "Byte");
    primitives.put("short", "Short");
    primitives.put("int", "Integer");
    primitives.put("long", "Long");
    primitives.put("float", "Float");
    primitives.put("double", "Double");
    primitives.put("boolean", "Boolean");
    primitives.put("char", "Char");

    defaults.put("byte", "0");
    defaults.put("short", "0");
    defaults.put("int", "0");
    defaults.put("long", "0l");
    defaults.put("float", "0f");
    defaults.put("double", "0.0");
    defaults.put("boolean", "false");
    defaults.put("char", "'a'");
  }

  public static boolean isPrimitive(String type) {
    return primitives.containsKey(type);
  }

  public static String getWrapper(String type) {
    return primitives.get(type);
  }

  public static String getDefault(String type) {
    return defaults.get(type);
  }

}
