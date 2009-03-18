package joist.web.pages;

import joist.util.TestCounter;
import joist.web.AbstractPage;
import joist.web.ClickKeywords;

public class RedirectOnInitPage extends AbstractPage {

    public static final TestCounter rendered = new TestCounter();

    @Override
    public void onInit() {
        ClickKeywords.redirect("/foo.htm");
    }

    @Override
    public boolean onRender() {
        RedirectOnInitPage.rendered.next();
        return false;
    }

}
