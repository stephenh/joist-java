package joist.web.controls.form;

import joist.web.util.HtmlWriter;

import org.bindgen.Binding;

public class CheckboxField extends AbstractField<CheckboxField> {

  public CheckboxField(Binding<Boolean> binding) {
    super(binding);
  }

  @Override
  protected CheckboxField getThis() {
    return this;
  }

  @Override
  public void render(HtmlWriter w) {
    w.append("<input id={} name={} type={} value={}", this.getFullId(), this.getId(), "checkbox", "true");
    if (this.getBoundValue() == Boolean.TRUE) {
      w.append(" checked={}", "checked");
    }
    w.append("{}/>", this.attributes);
  }

  @Override
  public boolean skipBindIfParameterIsNotPresent() {
    return false;
  }

}
