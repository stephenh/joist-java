package click.controls.form;

import org.exigencecorp.bindgen.NamedBinding;
import org.exigencecorp.util.Inflector;

import click.util.HtmlStringBuilderr;

public class SubmitButton implements Button {

    private String id;
    private String label;
    private Runnable runnable;

    public SubmitButton(Runnable runnable) {
        this.runnable = runnable;
        if (runnable instanceof NamedBinding) {
            this.setId(((NamedBinding) runnable).getName());
        } else {
            this.setId("Submit");
        }
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
        sb.append("<input id={} name={} type={} value={}/>", this.getId(), this.getId(), "submit", this.getLabel());
        return sb.toString();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
        if (this.label == null) {
            this.setLabel(Inflector.humanize(id));
        }
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
