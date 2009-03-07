package click.controls.form;

import org.apache.commons.lang.ObjectUtils;
import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.Inflector;

import click.ClickContext;
import click.CurrentContext;
import click.Page;

public abstract class AbstractField implements Field {

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
        ((Binding<Object>) this.binding).set(value);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        if (this.getLabel() == null) {
            this.setLabel(Inflector.humanize(id));
        }
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
