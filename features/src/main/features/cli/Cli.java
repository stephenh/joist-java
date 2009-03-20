package features.cli;

import joist.util.Reflection;

import org.apache.commons.lang.StringUtils;

import features.Registry;

public class Cli {

    public static void main(String[] args) {
        Registry.start();
        try {
            for (String arg : args) {
                String[] parts = StringUtils.split(arg, ".", 2);
                Object task = Reflection.newInstance("features.cli." + parts[0]);
                Reflection.invoke(parts[1], task);
            }
        } finally {
            Registry.stop();
        }
    }

}
