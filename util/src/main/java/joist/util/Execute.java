package joist.util;

import static java.util.Arrays.asList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Execute {

  private final List<String> commandPlusArgs = new ArrayList<String>();
  private final Map<String, String> env = new HashMap<String, String>();
  private final List<String> possiblePaths = Copy.list(".");
  private final List<String> possibleExtensions = Copy.list("", ".sh", ".bat", ".exe");
  private OutputStream out;
  private OutputStream err;
  private String input;

  public Execute(String command) {
    this.commandPlusArgs.add(command);
  }

  public Execute(String[] commandPlusArgs) {
    this.commandPlusArgs.addAll(Copy.list(commandPlusArgs));
  }

  public Execute addEnvPaths() {
    String splitOn = System.getProperty("path.separator");
    this.possiblePaths.addAll(asList(System.getenv("PATH").split(splitOn)));
    return this;
  }

  public Execute arg(String arg) {
    this.commandPlusArgs.add(arg);
    return this;
  }

  public Execute input(String input) {
    this.input = input;
    return this;
  }

  public Execute env(String key, String value) {
    this.env.put(key, value);
    return this;
  }

  public Execute path(String possiblePath) {
    this.possiblePaths.add(possiblePath);
    return this;
  }

  public Result toSystemOut() {
    this.out = System.out;
    this.err = System.err;
    return this.execute();
  }

  public Result toBuffer() {
    this.out = new ByteArrayOutputStream();
    this.err = new ByteArrayOutputStream();
    return this.execute();
  }

  private Result execute() {
    // Java tries to "help" by splitting on spaces unless you invoke the String[]-based methods.
    try {
      String[] commandPlusArgs = this.getCommandPlusArgsArray();
      log.trace("Executing {}", asList(commandPlusArgs));
      Process p = Runtime.getRuntime().exec(commandPlusArgs, this.getEnvArray());

      // To avoid blocking on one of the streams if the other/input is not done, fork them off into separate threads
      StreamGlobber out = new StreamGlobber(p.getInputStream(), this.out);
      StreamGlobber err = new StreamGlobber(p.getErrorStream(), this.err);
      out.start();
      err.start();
      if (this.input != null) {
        p.getOutputStream().write(this.input.getBytes());
      }
      p.getOutputStream().close();
      out.join();
      err.join();
      p.waitFor();

      Result result = new Result();
      result.out = out.toString();
      result.err = err.toString();
      result.success = p.exitValue() == 0;
      return result;
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Interrupted");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String[] getEnvArray() {
    String[] envArray = this.env == null ? new String[] {} : new String[this.env.size()];
    int i = 0;
    for (Entry<String, String> entry : this.env.entrySet()) {
      envArray[i++] = entry.getKey() + "=" + entry.getValue();
    }
    return envArray;
  }

  private String[] getCommandPlusArgsArray() {
    this.commandPlusArgs.add(0, this.resolve(this.commandPlusArgs.remove(0)));
    return Copy.array(String.class, this.commandPlusArgs);
  }

  private String resolve(String executable) {
    if (new File(executable).exists()) {
      return executable;
    }
    for (String path : this.possiblePaths) {
      for (String extension : this.possibleExtensions) {
        File file = new File(path, executable + extension);
        if (file.exists()) {
          return file.getAbsolutePath();
        }
      }
    }
    throw new RuntimeException("Couldn't find "
      + executable
      + " in possible paths "
      + this.possiblePaths
      + " with extensions "
      + this.possibleExtensions);
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
