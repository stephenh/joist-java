package joist.web.controls.form;

import joist.web.util.HtmlWriter;

import org.bindgen.Binding;

public class TextAreaField extends AbstractField<TextAreaField> {

  private int rows = 20;
  private int columns = 80;

  public TextAreaField() {
  }

  public TextAreaField(Binding<?> binding) {
    super(binding);
  }

  public void render(HtmlWriter w) {
    w.append("<textarea id={} name={} rows={} cols={}{}>{}</textarea>",//
      this.getFullId(),
      this.getId(),
      this.rows,
      this.columns,
      this.attributes,
      this.getBoundValue());
  }

  public TextAreaField size(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    return this;
  }

  public TextAreaField getThis() {
    return this;
  }

}
