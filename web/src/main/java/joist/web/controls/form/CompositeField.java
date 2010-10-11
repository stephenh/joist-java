package joist.web.controls.form;

import joist.web.util.HtmlWriter;

public class CompositeField extends AbstractField<CompositeField> {

  private final Field[] fields;

  public CompositeField(String label, Field... fields) {
    this.setLabel(label);
    this.fields = fields;
  }

  public void render(HtmlWriter w) {
    for (Field field : this.fields) {
      w.append("{}", field);
    }
  }

  public void onProcess() {
    for (Field field : this.fields) {
      field.onProcess();
    }
  }

  public CompositeField getThis() {
    return this;
  }

}
