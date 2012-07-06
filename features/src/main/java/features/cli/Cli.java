package features.cli;

import joist.util.Reflection;

import org.apache.commons.lang.StringUtils;

public class Cli {

  public static void main(String[] args) {
    for (String arg : args) {
      String[] parts = StringUtils.split(arg, ".", 2);
      Object task = Reflection.newInstance("features.cli." + parts[0]);
      Reflection.invoke(parts[1], task);
    }
  }

}
