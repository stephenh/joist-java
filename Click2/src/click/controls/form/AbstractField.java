package click.controls.form;

import org.exigencecorp.util.Inflector;
import org.mvel.DataConversion;
import org.mvel.PropertyAccessor;

import click.ClickContext;
import click.CurrentContext;
import click.Page;

public abstract class AbstractField implements Field {

    private String name;
    private String label;
    private String expression;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        if (this.getLabel() == null) {
            this.setLabel(Inflector.humanize(name));
        }
    }

    public String evaluateExpression() {
        return DataConversion.convert(PropertyAccessor.get(this.getExpression(), this.getPage()), String.class);
    }

    public void bindRequestValue(String value) {
        PropertyAccessor.set(this.getPage(), this.getExpression(), value);
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    private ClickContext getContext() {
        return CurrentContext.get();
    }

    private Page getPage() {
        return this.getContext().getPage();
    }

}
