package joist.web;

import java.util.Map;

import joist.web.util.HtmlWriter;
import joist.web.util.VelocityRenderer;

/** A good base class for users to extend. */
public abstract class AbstractPage extends AbstractContainer implements Page {

    private final ClickContext context = CurrentContext.get();

    public void onInit() {
    }

    public String getId() {
        return this.getClass().getName();
    }

    public void render(HtmlWriter w) {
        VelocityRenderer.render(this, w);
    }

    public Map<String, Object> getModel() {
        return this.context.getModel();
    }

    public void addModel(String name, Object value) {
        this.getModel().put(name, value);
    }

    public PageProcessor getProcessor() {
        return DefaultPageProcessor.INSTANCE;
    }

}
