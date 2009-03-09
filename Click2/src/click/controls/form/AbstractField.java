package click.controls.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.Inflector;

import click.ClickContext;
import click.CurrentContext;
import click.Page;

public abstract class AbstractField<T extends AbstractField<T>> implements Field {

    private String id;
    private String label;
    private Binding<?> binding;

    public String getBoundValue() {
        if (this.binding == null) {
            return "";
        }
        return ObjectUtils.toString(this.binding.get());
    }

    @SuppressWarnings("unchecked")
    public void onProcess() {
        String value = this.getContext().getRequest().getParameter(this.getId());
        if (value == null) {
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
