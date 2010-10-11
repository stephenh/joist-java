package joist.web;

import joist.web.util.HtmlWriter;
import joist.web.util.VelocityRenderer;

/** A good base class for users to extend. */
public abstract class AbstractPage extends AbstractContainer implements Page {

  public void onInit() {
  }

  @Override
  public String getId() {
    return "page";
  }

  @Override
  public void render(HtmlWriter w) {
    VelocityRenderer.render(this, w);
  }

  /** @return the control to render--defaults to this, but could be a layout control */
  @Override
  public Control getLayout() {
    return this;
  }

  @Override
  public PageProcessor getProcessor() {
    return DefaultPageProcessor.INSTANCE;
  }

  @Override
  public boolean isAllowedViaUrl(Object converted) {
    return true;
  }

  public boolean isPost() {
    return CurrentContext.get().isPost();
  }

}
