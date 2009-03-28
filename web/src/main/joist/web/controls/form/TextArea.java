package joist.web.controls.form;

import joist.web.util.HtmlWriter;

import org.exigencecorp.bindgen.Binding;

public class TextArea extends AbstractField<TextArea> {

    private int rows = 20;
    private int columns = 80;

    public TextArea() {
    }

    public TextArea(Binding<?> binding) {
        super(binding);
    }

    public void render(HtmlWriter w) {
        w.append("<textarea id={} name={} rows={} cols={}>{}</textarea>",//
            this.getFullId(),
            this.getId(),
            this.rows,
            this.columns,
            "text",
            this.getBoundValue());
    }

    public TextArea size(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        return this;
    }

    public TextArea getThis() {
        return this;
    }

}
