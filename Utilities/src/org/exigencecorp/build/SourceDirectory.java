package org.exigencecorp.build;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.util.Copy;

public class SourceDirectory {

    private final String input;
    private String output;

    public SourceDirectory(String path) {
        this.input = path;
    }

    public SourceDirectory output(String output) {
        this.output = output;
        return this;
    }

    private List<String> findFiles() {
        List<String> paths = new ArrayList<String>();
        List<File> dirs = Copy.list(new File(this.input));
        while (dirs.size() != 0) {
            for (File file : dirs.remove(0).listFiles()) {
                if (file.isDirectory()) {
                    dirs.add(file);
                } else {
                    paths.add(file.getPath());
                }
            }
        }
        return paths;
    }

    public void compile() {
        new File(this.output).mkdirs();
        try {
            Class<?> clazz = Class.forName("com.sun.tools.javac.Main");
            Method compile = clazz.getMethod("compile", new Class[] { String[].class });
            Object instance = clazz.newInstance();

            List<String> paths = this.findFiles();
            paths.add(0, this.output);
            paths.add(0, "-d");

            Integer result = ((Integer) compile.invoke(instance, new Object[] { Copy.array(String.class, paths) })).intValue();
            if (result != 0) {
                throw new Error("An error occurred");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
