package joist.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Map.Entry;

public class SystemProperties {

    public static void loadFromFileIfExists(String path) {
        if (!new File(path).exists()) {
            return;
        }

        Properties p = new Properties();
        try {
            p.load(new FileReader(path));
        } catch (IOException io) {
            throw new RuntimeException(io);
        }

        for (Entry<Object, Object> e : p.entrySet()) {
            if (!System.getProperties().contains(e.getKey())) {
                System.setProperty((String) e.getKey(), (String) e.getValue());
            }
        }
    }

}
