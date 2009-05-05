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
    private Binding<List<T>> listBinding;

    public SelectField(Binding<?> binding) {
        super(binding);
    }

    /** For now we need a temp binding because f'ing erasure makes the listing binding contained type unavailable. */
    public SelectField(Binding<?> tempBinding, Binding<List<T>> listBinding) {
        super(tempBinding);
        this.listBinding = listBinding;
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

    public SelectField<T> multiple(boolean multiple) {
        if (multiple) {
            this.getAttributes().put("multiple", "multiple");
        } else {
            this.getAttributes().remove("multiple");
        }
        return this;
    }

    public SelectField<T> size(int size) {
        this.getAttributes().put("size", Integer.toString(size));
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onProcess() {
        if (this.listBinding == null) {
            super.onProcess();
            return;
        }

        String[] value = this.getContext().getRequest().getParameterValues(this.getId());
        List<T> newValues = new ArrayList<T>();
        if (value != null) {
            for (String part : value) {
                // Use the text converter because this is coming from a user
                Object converted = this.getProcessConverterRegistry().convert(part, this.getBinding().getType());
                newValues.add((T) converted);
            }
        }
        this.listBinding.set(newValues);
    }

    @Override
    public void render(HtmlWriter w) {
        w.line("<select id={} name={}{}>", this.getFullId(), this.getId(), this.attributes);
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
            boolean selected = (this.listBinding == null) ? option.equals(this.getBoundValue()) : this.listBinding.get().contains(option);
            if (selected) {
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
