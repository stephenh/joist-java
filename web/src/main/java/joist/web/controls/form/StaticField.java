package joist.web.controls.form;

import joist.web.util.HtmlWriter;

import org.bindgen.Binding;

public class StaticField extends AbstractField<StaticField> {

  public StaticField() {
  }

  public StaticField(Binding<?> binding) {
    super(binding);
  }

  @Override
  public void onProcess() {
    // do nothing
  }

  public void render(HtmlWriter w) {
    w.append("<span id={}{}>{}</span>", this.getFullId(), this.attributes, this.getBoundValue());
  }

  public StaticField getThis() {
    return this;
  }

}
