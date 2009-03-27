package joist.web;

import joist.web.util.HtmlWriter;
import joist.web.util.VelocityRenderer;

public abstract class AbstractLayoutControl extends AbstractControl {

    public void render(HtmlWriter w) {
        VelocityRenderer.render(this, w);
    }

}
