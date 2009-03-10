package joist.web.pages;

import joist.web.AbstractPage;
import joist.web.ClickKeywords;
import joist.web.controls.form.Form;
import joist.web.controls.form.SubmitButton;

import org.exigencecorp.bindgen.Bindable;
import org.exigencecorp.util.TestCounter;

import bindgen.joist.web.pages.RedirectOnClickPageBinding;

@Bindable
public class RedirectOnClickPage extends AbstractPage {

    public static final TestCounter rendered = new TestCounter();
    public static final TestCounter lineAfterRedirect = new TestCounter();
    public Form form = new Form("form");

    @Override
    public void onInit() {
        RedirectOnClickPageBinding b = new RedirectOnClickPageBinding(this);
        this.form.add(new SubmitButton(b.submit()));
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
