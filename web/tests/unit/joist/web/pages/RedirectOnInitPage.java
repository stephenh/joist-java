package joist.web.pages;

import joist.util.TestCounter;
import joist.web.AbstractPage;
import joist.web.ClickKeywords;
import joist.web.util.HtmlWriter;

public class RedirectOnInitPage extends AbstractPage {

    public static final TestCounter rendered = new TestCounter();

    @Override
    public void onInit() {
        ClickKeywords.redirect(RedirectOnInitPage.class);
    }

    @Override
    public void render(HtmlWriter w) {
        super.render(w);
        RedirectOnInitPage.rendered.next();
    }

}
