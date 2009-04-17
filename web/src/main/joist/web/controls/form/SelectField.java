package joist.web.controls.form;

import java.util.ArrayList;
import java.util.List;

import joist.converter.ConverterRegistry;
import joist.util.Copy;
import joist.web.CurrentContext;
import joist.web.util.HtmlWriter;

import org.exigencecorp.bindgen.Binding;

public class SelectField<T> extends AbstractField<SelectField<T>> {

    private List<T> options = new ArrayList<T>();
    private boolean showBlank = false;

    public SelectField(Binding<?> binding) {
        super(binding);
    }

    public SelectField<T> options(List<T> options) {
        this.options = options;
        return this;
    }

    public SelectField<T> options(T... options) {
        this.options = Copy.list(options);
        return this;
    }

    public SelectField<T> showBlank() {
        this.showBlank = true;
        return this;
    }

    @Override
    public void render(HtmlWriter w) {
        w.line("<select id={} name={}>", this.getFullId(), this.getId());
        if (this.showBlank) {
            String blankId = this.getFullId() + "-blank";
            if (this.getBoundValue() == null) {
                w.line("<option id={} selected=\"selected\" value=\"\"></option>", blankId);
            } else {
                w.line("<option id={} value=\"\"></option>", blankId);
            }
        }
        int i = 0;
        for (T option : this.options) {
            String id = this.getFullId() + "-" + i++;
            String forValue = CurrentContext.get().getWebConfig().getUrlConverterRegistry().convert(option, String.class);
            String forLabel = CurrentContext.get().getWebConfig().getTextConverterRegistry().convert(option, String.class);
            if (option.equals(this.getBoundValue())) {
                w.line("<option id={} selected=\"selected\" value={}>{}</option>", id, forValue, forLabel);
            } else {
                w.line("<option id={} value={}>{}</option>", id, forValue, forLabel);
            }
        }
        w.line("</select>");
    }

    @Override
    protected SelectField<T> getThis() {
        return this;
    }

    @Override
    protected ConverterRegistry getProcessConverterRegistry() {
        return CurrentContext.get().getWebConfig().getUrlConverterRegistry();
    }

}
