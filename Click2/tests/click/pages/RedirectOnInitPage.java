package click.pages;

import org.exigencecorp.util.TestCounter;

import click.AbstractPage;
import click.ClickKeywords;

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
