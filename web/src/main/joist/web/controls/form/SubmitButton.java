package joist.web.controls.form;

import joist.util.Inflector;
import joist.web.util.HtmlWriter;

import org.exigencecorp.bindgen.NamedBinding;

public class SubmitButton implements Button {

    private String id;
    private String label;
    private Runnable runnable;

    public SubmitButton(Runnable runnable) {
        this.runnable = runnable;
        if (runnable instanceof NamedBinding) {
            this.id(((NamedBinding) runnable).getName());
        } else {
            this.id("submit");
        }
    }

    public void onProcess() {
        this.runnable.run();
    }

    public void render(HtmlWriter w) {
        w.append("<input id={} name={} type={} value={}/>", this.getId(), this.getId(), "submit", this.getLabel());
    }

    public SubmitButton id(String id) {
        this.setId(id);
        this.setLabel(Inflector.humanize(id));
        return this;
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

}
