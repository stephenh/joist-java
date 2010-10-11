package joist.web.pages;

import joist.util.TestCounter;
import joist.web.AbstractPage;
import joist.web.WebKeywords;
import joist.web.util.HtmlWriter;

public class RedirectOnInitPage extends AbstractPage {

  public static final TestCounter rendered = new TestCounter();

  @Override
  public void onInit() {
    WebKeywords.redirect(RedirectOnInitPage.class);
  }

  @Override
  public void render(HtmlWriter w) {
    super.render(w);
    RedirectOnInitPage.rendered.next();
  }

}
