package joist.sourcegen;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ParsedNameTest {

  @Test
  public void testGenerics() {
    ParsedName n = ParsedName.parse("com.foo.Type<com.foo.Bar>");
    assertThat(n.packageName, is("com.foo"));
    assertThat(n.simpleName, is("Type"));
    assertThat(n.simpleNameWithGenerics, is("Type<com.foo.Bar>"));
    assertThat(n.getFullName(), is("com.foo.Type"));
  }

  @Test
  public void testNestedGenerics() {
    ParsedName n = ParsedName.parse("com.foo.Type<com.foo.Bar<String>>");
    assertThat(n.packageName, is("com.foo"));
    assertThat(n.simpleName, is("Type"));
    assertThat(n.simpleNameWithGenerics, is("Type<com.foo.Bar<String>>"));
    assertThat(n.getFullName(), is("com.foo.Type"));
  }

  @Test
  public void testNoPackage() {
    ParsedName n = ParsedName.parse("Type<com.foo.Bar>");
    assertThat(n.packageName, is(nullValue()));
    assertThat(n.simpleName, is("Type"));
    assertThat(n.simpleNameWithGenerics, is("Type<com.foo.Bar>"));
    assertThat(n.getFullName(), is("Type"));
  }

}
