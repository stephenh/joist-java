package joist.web;

import java.io.Writer;

import joist.web.util.HtmlWriter;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.Renderable;

public abstract class AbstractControl implements Control, Renderable {

    private String id;
    private Control parent;

    protected AbstractControl() {
        CurrentContext.addControl(this);
    }

    public void onProcess() {
    }

    @Override
    public final boolean render(InternalContextAdapter context, Writer writer) {
        this.render((writer instanceof HtmlWriter) ? (HtmlWriter) writer : new HtmlWriter(writer));
        return true;
    }

    public String getFullId() {
        if (this.getParent() != null && !(this.getParent() instanceof Page)) {
            return this.getParent().getId() + "-" + this.getId();
        } else {
            return this.getId();
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Control getParent() {
        return this.parent;
    }

    public void setParent(Control parent) {
        this.parent = parent;
    }

}
