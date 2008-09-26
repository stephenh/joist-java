package org.exigencecorp.util;

import java.net.InetAddress;

import org.apache.commons.lang.StringUtils;

public class HostName {

    private static String hostName;

    static {
        // Note: we have to assign HostName.hostName before any Log.xxx calls as LogConfiguration
        // may init in production with some SMTP Log Appenders that HostName.get() before our
        // static initializer has actually completed.
        try {
            String javaHostName = StringUtils.defaultString(InetAddress.getLocalHost().getHostName()).toLowerCase();
            if (javaHostName.indexOf('.') > -1) {
                HostName.hostName = javaHostName.substring(0, javaHostName.indexOf('.'));
                Log.info("Hostname reported by Java: {}. Interpreted hostname as {}.", javaHostName, HostName.get());
            } else {
                HostName.hostName = javaHostName;
                Log.debug("Hostname reported and interpreted as {}.", HostName.get());
            }
        } catch (java.net.UnknownHostException uhe) {
        }
    }

    private HostName() {
    }

    public static String get() {
        if (HostName.hostName == null) {
            throw new RuntimeException("Could not determine hostname for integrity check.");
        }
        return HostName.hostName;
    }

}
