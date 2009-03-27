package joist.web;

import joist.web.util.HtmlWriter;
import joist.web.util.VelocityRenderer;

/** A good base class for users to extend. */
public abstract class AbstractPage extends AbstractContainer implements Page {

    public void onInit() {
    }

    public String getId() {
        return "page";
    }

    public void render(HtmlWriter w) {
        VelocityRenderer.render(this, w);
    }

    /** @return the control to render--defaults to this, but could be a layout control */
    public Control getLayout() {
        return this;
    }

    public PageProcessor getProcessor() {
        return DefaultPageProcessor.INSTANCE;
    }

}
