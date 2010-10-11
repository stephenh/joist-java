package joist.web;

import joist.util.Inflector;
import joist.web.util.HtmlWriter;
import joist.web.util.VelocityRenderer;

public abstract class AbstractLayoutControl extends AbstractContainer {

  public AbstractLayoutControl() {
    this.setId(Inflector.uncapitalize(this.getClass().getSimpleName()));
  }

  public void render(HtmlWriter w) {
    VelocityRenderer.render(this, w);
  }

}
