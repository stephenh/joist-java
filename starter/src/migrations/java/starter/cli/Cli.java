package starter.cli;

import joist.util.Reflection;
import joist.util.SystemProperties;

import org.apache.commons.lang.StringUtils;

/** A really simple class to bootstrap from the command line. */
public class Cli {

  /** Passed JoistCli.codegen, JoistCli.fixPermissions, will instantiate JoistCli class and call each method. */
  public static void main(String[] args) {
    SystemProperties.loadFromFileIfExists("./build.properties");
    for (String arg : args) {
      String[] parts = StringUtils.split(arg, ".", 2);
      Object task = Reflection.newInstance("starter.cli." + parts[0]);
      Reflection.invoke(parts[1], task);
    }
  }

}
