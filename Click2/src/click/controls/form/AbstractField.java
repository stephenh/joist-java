package click.controls.form;

import org.apache.commons.lang.ObjectUtils;
import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.Inflector;

import click.ClickContext;
import click.CurrentContext;
import click.Page;

public abstract class AbstractField implements Field {

    private String name;
    private String label;
    private Binding binding;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        if (this.getLabel() == null) {
            this.setLabel(Inflector.humanize(name));
        }
    }

    public String getBoundValue() {
        if (this.binding == null) {
            return "";
        }
        return ObjectUtils.toString(this.binding.get());
    }

    public void bindRequestValue(String value) {
        // PropertyAccessor.set(this.getPage(), this.getExpression(), value);
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

    public Binding getBinding() {
        return this.binding;
    }

    public void setBinding(Binding binding) {
        this.binding = binding;
    }

}
