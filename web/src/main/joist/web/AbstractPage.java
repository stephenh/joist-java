package joist.web;

import joist.web.util.HtmlWriter;
import joist.web.util.VelocityRenderer;

/** A good base class for users to extend. */
public abstract class AbstractPage extends AbstractContainer implements Page {

    public void onInit() {
    }

    public String getId() {
        return this.getClass().getName();
    }

    public void render(HtmlWriter w) {
        VelocityRenderer.render(this, w);
    }

    public PageProcessor getProcessor() {
        return DefaultPageProcessor.INSTANCE;
    }

}
