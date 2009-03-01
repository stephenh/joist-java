package click.controls.form;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.bindgen.Binding;

import click.util.HtmlStringBuilderr;

public class TextField extends AbstractField {

    public TextField(Binding<?> binding) {
        this.setName(StringUtils.capitalize(binding.getName()));
        this.setBinding(binding);
    }

    public TextField(String name, Binding<?> binding) {
        this.setName(name);
        this.setBinding(binding);
    }

    public String toString() {
        HtmlStringBuilderr sb = new HtmlStringBuilderr();
        sb.append("<input id={} name={} type={} value={}/>", this.getName(), this.getName(), "text", this.getBoundValue());
        return sb.toString();
    }

}
