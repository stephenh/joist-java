package org.exigencecorp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.StringUtils;

public class Execute {

    private Execute() {
    }

    public static boolean toSystemOut(String command) throws Exception {
        return Execute.toSystemOut(command, null);
    }

    public static boolean toSystemOut(String command, String input) throws Exception {
        return Execute.command(new String[] { command }, input, System.out, System.err);
    }

    public static Result toResult(String[] commandPlusArgs, String input) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        Result result = new Result();
        result.success = Execute.command(commandPlusArgs, input, out, err);
        result.out = StringUtils.chomp(out.toString());
        result.err = StringUtils.chomp(err.toString());
        return result;
    }

    private static boolean command(String commandPlusArgs[], String input, OutputStream stdOut, OutputStream stdErr) throws Exception {
        // Java tries to "help" by splitting on spaces unless you invoke the String[]-based methods.
        Process p = Runtime.getRuntime().exec(commandPlusArgs);

        // To avoid blocking on one of the streams if the other/input is not done, fork them off into separate threads
        StreamGlobber out = new StreamGlobber(p.getInputStream(), stdOut);
        StreamGlobber err = new StreamGlobber(p.getErrorStream(), stdErr);
        out.start();
        err.start();

        if (input != null) {
            p.getOutputStream().write(input.getBytes());
        }

        p.getOutputStream().close();

        out.join();
        err.join();

        p.waitFor();
        return p.exitValue() == 0;
    }

    private static class StreamGlobber extends Thread {
        private final InputStream input;
        private final OutputStream output;

        StreamGlobber(InputStream input, OutputStream output) {
            this.input = input;
            this.output = output;
        }

        public void run() {
            try {
                byte[] buffer = new byte[1024 * 4];
                int n = 0;
                while (-1 != (n = this.input.read(buffer))) {
                    this.output.write(buffer, 0, n);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public static class Result {
        public boolean success;
        public String out;
        public String err;
    }

}
