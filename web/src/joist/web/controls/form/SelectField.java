package joist.web.controls.form;

import java.util.ArrayList;
import java.util.List;

import joist.web.CurrentContext;
import joist.web.util.HtmlWriter;

import org.exigencecorp.bindgen.Binding;

public class SelectField<T> extends AbstractField<SelectField<T>> {

    private List<T> options = new ArrayList<T>();
    private boolean showBlank = false;

    public SelectField(Binding<T> binding) {
        super(binding);
    }

    public SelectField<T> options(List<T> options) {
        this.options = options;
        return this;
    }

    public SelectField<T> showBlank() {
        this.showBlank = true;
        return this;
    }

    @Override
    public void render(HtmlWriter w) {
        w.line("<select id={} name={}>", this.getId(), this.getId());
        if (this.showBlank) {
            if (this.getBoundValue() == null) {
                w.line("<option selected=\"selected\" value=\"\"></option>");
            } else {
                w.line("<option value=\"\"></option>");
            }
        }
        for (T option : this.options) {
            String forValue = CurrentContext.get().getClickConfig().getUrlConverterRegistry().convert(option, String.class);
            String forLabel = CurrentContext.get().getClickConfig().getTextConverterRegistry().convert(option, String.class);
            if (option.equals(this.getBoundValue())) {
                w.line("<option selected=\"selected\" value={}>{}</option>", forValue, forLabel);
            } else {
                w.line("<option value={}>{}</option>", forValue, forLabel);
            }
        }
        w.line("</select>");
    }

    @Override
    protected SelectField<T> getThis() {
        return this;
    }

}
