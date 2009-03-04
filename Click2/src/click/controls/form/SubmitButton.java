package click.controls.form;

import click.util.HtmlStringBuilderr;

public class SubmitButton implements Button {

    private String id;
    private Runnable runnable;

    public SubmitButton(Runnable runnable) {
        this.setId("Submit");
        this.runnable = runnable;
    }

    public SubmitButton(String id, Runnable runnable) {
        this.setId(id);
        this.runnable = runnable;
    }

    public void onProcess() {
        this.runnable.run();
    }

    public String toString() {
        HtmlStringBuilderr sb = new HtmlStringBuilderr();
        sb.append("<input id={} name={} type={} value={}/>", this.getId(), this.getId(), "submit", this.getId());
        return sb.toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
