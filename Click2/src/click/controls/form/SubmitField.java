package click.controls.form;

import click.util.HtmlStringBuilderr;

public class SubmitField extends AbstractField {

    private Runnable runnable;

    public SubmitField(Runnable runnable) {
        this.setName("Submit");
        this.runnable = runnable;
    }

    public void onProcess() {
        this.runnable.run();
    }

    public String toString() {
        HtmlStringBuilderr sb = new HtmlStringBuilderr();
        sb.append("<input id={} name={} type={} value={}/>", this.getName(), this.getName(), "submit", this.getBoundValue());
        return sb.toString();
    }

}
