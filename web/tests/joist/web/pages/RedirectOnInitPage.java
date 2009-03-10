package joist.web.pages;

import joist.web.AbstractPage;
import joist.web.ClickKeywords;

import org.exigencecorp.util.TestCounter;


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
