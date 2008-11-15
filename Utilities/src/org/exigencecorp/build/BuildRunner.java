package org.exigencecorp.build;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.exigencecorp.util.Reflection;

public class BuildRunner {

    public static void run(Object build, String[] args) {
        System.out.println("Building...");
        for (String arg : args) {
            System.out.println("  " + arg);
            try {
                if (arg.indexOf('.') == -1) {
                    Method method = build.getClass().getMethod(arg);
                    Reflection.invoke(method, build);
                } else {
                    String[] parts = arg.split("\\.");
                    Field field = build.getClass().getField(parts[0]);
                    Object fieldValue = field.get(build);
                    Method method = fieldValue.getClass().getMethod(parts[1]);
                    Reflection.invoke(method, fieldValue);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        System.out.println("...done");
        System.exit(0);
    }

}
