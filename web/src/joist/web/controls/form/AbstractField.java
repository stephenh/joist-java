package joist.web.controls.form;

import java.util.ArrayList;
import java.util.List;

import joist.util.Inflector;
import joist.web.ClickContext;
import joist.web.CurrentContext;
import joist.web.Page;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.bindgen.Binding;

public abstract class AbstractField<T extends AbstractField<T>> implements Field {

    private String id;
    private String label;
    private Binding<?> binding;

    protected AbstractField() {
    }

    protected AbstractField(Binding<?> binding) {
        this.id(StringUtils.capitalize(binding.getName()));
        this.setBinding(binding);
    }

    public Object getBoundValue() {
        if (this.binding == null) {
            return null;
        }
        return this.binding.get();
    }

    @SuppressWarnings("unchecked")
    public void onProcess() {
        String value = this.getContext().getRequest().getParameter(this.getId());
        if (value == null & this.skipBindIfParameterIsNotPresent()) {
            return; // We would have at least gotten a "" if the field was really submitted
        }
        // Use the text converter because this is coming from a user
        Object converted = CurrentContext.get().getClickConfig().getTextConverterRegistry().convert(value, this.binding.getType());
        // do this here or inside a Converter?
        if ("".equals(converted)) {
            converted = null;
        }
        ((Binding<Object>) this.binding).set(converted);
    }

    protected boolean skipBindIfParameterIsNotPresent() {
        return true;
    }

    public List<String> getErrors() {
        List<String> errors = new ArrayList<String>();
        return errors;
    }

    protected abstract T getThis();

    public T id(String id) {
        this.id = id;
        this.setLabel(Inflector.humanize(id));
        return this.getThis();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    protected ClickContext getContext() {
        return CurrentContext.get();
    }

    protected Page getPage() {
        return this.getContext().getPage();
    }

    public Binding<?> getBinding() {
        return this.binding;
    }

    public void setBinding(Binding<?> binding) {
        this.binding = binding;
    }

}
