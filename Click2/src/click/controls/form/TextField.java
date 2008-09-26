package click.controls.form;

import click.util.HtmlStringBuilder;

public class TextField extends AbstractField {

    public TextField(String name, String expression) {
        this.setName(name);
        this.setExpression(expression);
    }

    public String toString() {
        HtmlStringBuilder sb = new HtmlStringBuilder();
        sb.append("<input id={} name={} type={} value={}/>", this.getName(), this.getName(), "text", this.evaluateExpression());
        return sb.toString();
    }

}
