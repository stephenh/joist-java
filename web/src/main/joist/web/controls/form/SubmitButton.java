package joist.web.controls.form;

import joist.util.Inflector;
import joist.web.AbstractControl;
import joist.web.CurrentContext;
import joist.web.util.HtmlWriter;

import org.exigencecorp.bindgen.NamedBinding;

public class SubmitButton extends AbstractControl implements Button {

    private String label;
    private Runnable runnable;
    private boolean isDefaultButton;

    public SubmitButton(Runnable runnable) {
        this.runnable = runnable;
        if (runnable instanceof NamedBinding) {
            this.id(((NamedBinding) runnable).getName());
        } else {
            this.id("submit");
        }
    }

    @Override
    public void onProcess() {
        String value = CurrentContext.get().getRequest().getParameter("_formButton");
        boolean submitted = value != null && value.equals(this.getLabel());
        boolean defaulted = value == null && this.isDefaultButton; // IE does not always send the value
        if (submitted || defaulted) {
            this.runnable.run();
        }
    }

    @Override
    public void render(HtmlWriter w) {
        w.append("<input id={} name={} type={} value={}/>", this.getFullId(), "_formButton", "submit", this.getLabel());
    }

    public SubmitButton id(String id) {
        this.setId(id);
        this.setLabel(Inflector.humanize(id));
        return this;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isDefaultButton() {
        return this.isDefaultButton;
    }

    public void setDefaultButton(boolean isDefaultButton) {
        this.isDefaultButton = isDefaultButton;
    }

}
