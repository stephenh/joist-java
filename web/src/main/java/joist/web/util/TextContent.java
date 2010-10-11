package joist.web.util;

import joist.web.CurrentContext;

import org.bindgen.Binding;

/** A wrapper for content to show the user--could be just a string, a binding, etc. */
public class TextContent {

  private Object content;

  public void set(Object content) {
    this.content = content;
  }

  public String get() {
    Object value = this.content;
    if (this.content instanceof Binding<?>) {
      value = ((Binding<?>) this.content).get();
    }
    String valueAsString = CurrentContext.get().getWebConfig().getTextConverterRegistry().convert(value, String.class);
    return valueAsString;
  }

}
