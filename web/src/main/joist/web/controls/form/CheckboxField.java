package joist.web.controls.form;

import joist.web.util.HtmlWriter;

import org.exigencecorp.bindgen.Binding;

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
        String checked = this.getBoundValue() == Boolean.TRUE ? "checked" : "unchecked";
        w.append("<input id={} name={} type={} value={} checked={}/>", this.getFullId(), this.getId(), "checkbox", "true", checked);
    }

    @Override
    public boolean skipBindIfParameterIsNotPresent() {
        return false;
    }

}
