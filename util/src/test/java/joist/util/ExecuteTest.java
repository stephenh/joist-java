package joist.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import joist.util.Execute.BufferedResult;
import joist.util.Execute.Result;

import org.junit.Test;

public class ExecuteTest {

  @Test
  public void toBufferOut() {
    Execute e = new Execute("ls");
    BufferedResult r = e.toBuffer();
    assertThat(r.out.contains("README.markdown"), is(true));
    assertThat(r.success, is(true));
    assertThat(r.exitValue, is(0));
  }

  @Test
  public void toBufferErr() {
    Execute e = new Execute("ls").arg("foo");
    BufferedResult r = e.toBuffer();
    assertThat(r.err.contains("No such file"), is(true));
    assertThat(r.success, is(false));
    assertThat(r.exitValue, is(2));
  }

  @Test
  public void toSystemOut() {
    Execute e = new Execute("ls");
    Result r = e.toSystemOut();
    assertThat(r.success, is(true));
    assertThat(r.exitValue, is(0));
  }

  @Test
  public void toSystemOutFailure() {
    Execute e = new Execute("ls").arg("foo");
    Result r = e.toSystemOut();
    assertThat(r.success, is(false));
    assertThat(r.exitValue, is(2));
  }

  @Test
  public void toFileOut() {
    File out = new File("target/out.txt");
    // make sure we don't assert against stale results
    if (out.exists()) {
      out.delete();
    }
    Execute e = new Execute("ls");
    Result r = e.toFile("target/out.txt");
    assertThat(r.success, is(true));
    assertThat(r.exitValue, is(0));
    assertThat(Read.fromFile(out).contains("README.markdown"), is(true));
  }

  @Test
  public void addAllEnv() {
    // addAllEnv should pass on the HOME variable
    String out = new Execute("bash").addEnvPaths().addAllEnv().arg("-c").arg("echo ${HOME}").toBuffer().out;
    assertThat(out, is(System.getenv("HOME") + "\n"));
  }

  @Test
  public void envPathsAreTheDefault() {
    Execute e = new Execute("ls");
    assertThat(e.toBuffer().exitValue, is(0));
  }

  @Test
  public void allEnvIsTheDefault() {
    Execute e = new Execute("bash").arg("-c").arg("echo ${HOME}");
    assertThat(e.toBuffer().out, is(System.getenv("HOME") + "\n"));
  }

  @Test(expected = RuntimeException.class)
  public void pathCanBeCleared() {
    Execute e = new Execute("ls").clearPath();
    e.toBuffer();
  }

  @Test
  public void envCanBeCleared() {
    Execute e = new Execute("bash").clearEnv().arg("-c").arg("echo ${HOME}");
    assertThat(e.toBuffer().out, is("\n"));
  }
}
