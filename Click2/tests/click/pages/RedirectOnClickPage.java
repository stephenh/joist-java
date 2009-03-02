package click.pages;

import org.exigencecorp.bindgen.Bindable;
import org.exigencecorp.util.TestCounter;

import bindgen.click.pages.RedirectOnClickPageBinding;
import click.AbstractPage;
import click.ClickKeywords;
import click.controls.form.Form;
import click.controls.form.SubmitField;

@Bindable
public class RedirectOnClickPage extends AbstractPage {

    public static final TestCounter rendered = new TestCounter();
    public static final TestCounter lineAfterRedirect = new TestCounter();
    public Form form = new Form("form");

    @Override
    public void onInit() {
        RedirectOnClickPageBinding b = new RedirectOnClickPageBinding(this);
        this.form.add(new SubmitField(b.submit()));
    }

    @Override
    public boolean onRender() {
        RedirectOnClickPage.rendered.next();
        return false;
    }

    public void submit() {
        ClickKeywords.redirect("/foo.htm");
        RedirectOnClickPage.lineAfterRedirect.next();
    }

}
