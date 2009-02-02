package click.controls.form;

import click.util.HtmlStringBuilder;

public class SubmitField extends AbstractField {

    private Runnable runnable;

    public SubmitField(Runnable runnable) {
        this.setName("Submit");
        this.runnable = runnable;
    }

    public String toString() {
        HtmlStringBuilder sb = new HtmlStringBuilder();
        sb.append("<input id={} name={} type={} value={}/>", this.getName(), this.getName(), "submit", this.getBoundValue());
        return sb.toString();
    }

}
