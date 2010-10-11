package joist.web.controls;

import java.util.Map.Entry;

import joist.web.CurrentContext;
import joist.web.Page;
import joist.web.WebContext;

public class ActionLink extends PageLink {

  private Runnable runnable;

  public ActionLink(Class<? extends Page> pageClass) {
    super(pageClass);
  }

  public static ActionLink forCurrentPage() {
    Page page = CurrentContext.get().getPage();
    ActionLink link = new ActionLink(page.getClass());
    for (Entry<String, String[]> e : CurrentContext.get().getParameters().entrySet()) {
      link.param(e.getKey(), e.getValue()[0]);
    }
    return link;
  }

  public ActionLink onClick(Runnable runnable) {
    this.runnable = runnable;
    return this;
  }

  @Override
  public void onProcess() {
    if ("true".equals(this.getContext().getRequest().getParameter(this.getFullId()))) {
      this.runnable.run();
    }
  }

  private WebContext getContext() {
    return CurrentContext.get();
  }

  @Override
  protected String getQueryString() {
    this.param(this.getFullId(), "true");
    return super.getQueryString();
  }

}
