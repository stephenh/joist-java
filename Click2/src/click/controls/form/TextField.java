package click.controls.form;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.bindgen.Binding;

import click.util.HtmlWriter;

public class TextField extends AbstractField<TextField> {

    public TextField(Binding<?> binding) {
        this.setId(StringUtils.capitalize(binding.getName()));
        this.setBinding(binding);
    }

    public TextField(String id, Binding<?> binding) {
        this.setId(id);
        this.setBinding(binding);
    }

    public void render(HtmlWriter w) {
        w.append("<input id={} name={} type={} value={}/>", this.getId(), this.getId(), "text", this.getBoundValue());
    }

    public TextField getThis() {
        return this;
    }

}
