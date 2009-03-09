package click.controls.form;

import org.exigencecorp.bindgen.Binding;

import click.util.HtmlWriter;

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
        String checked = "true".equals(this.getBoundValue()) ? "checked" : "unchecked";
        w.append("<input id={} name={} type={} value={} checked={}/>", this.getId(), this.getId(), "checkbox", "true", checked);
    }

    @Override
    public boolean skipBindIfParameterIsNotPresent() {
        return false;
    }

}
