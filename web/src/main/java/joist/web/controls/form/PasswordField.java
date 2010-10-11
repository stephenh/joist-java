package joist.web.controls.form;

import joist.web.util.HtmlWriter;

import org.bindgen.Binding;

public class PasswordField extends AbstractField<PasswordField> {

  public PasswordField() {
  }

  public PasswordField(Binding<?> binding) {
    super(binding);
  }

  public void render(HtmlWriter w) {
    w.append("<input id={} name={} type={} value={}{}/>", this.getFullId(), this.getId(), "password", this.getBoundValue(), this.attributes);
  }

  public PasswordField getThis() {
    return this;
  }

}
