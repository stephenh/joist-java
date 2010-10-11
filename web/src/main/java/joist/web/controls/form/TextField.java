package joist.web.controls.form;

import joist.web.util.HtmlWriter;

import org.bindgen.Binding;

public class TextField extends AbstractField<TextField> {

  public TextField() {
  }

  public TextField(Binding<?> binding) {
    super(binding);
  }

  public void render(HtmlWriter w) {
    w.append("<input id={} name={} type={} value={}{}/>", this.getFullId(), this.getId(), "text", this.getBoundValue(), this.attributes);
  }

  public TextField getThis() {
    return this;
  }

}
