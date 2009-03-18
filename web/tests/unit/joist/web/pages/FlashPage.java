package joist.web.pages;

import joist.web.AbstractPage;
import joist.web.ClickKeywords;
import joist.web.controls.form.Form;
import joist.web.controls.form.SubmitButton;

import org.exigencecorp.bindgen.Bindable;

import bindgen.joist.web.pages.FlashPageBinding;

@Bindable
public class FlashPage extends AbstractPage {

    public Form form = new Form("form");

    @Override
    public void onInit() {
        FlashPageBinding b = new FlashPageBinding(this);
        this.form.add(new SubmitButton(b.submit()));
    }

    public void submit() {
        ClickKeywords.flash("message", "success");
        ClickKeywords.redirect("/flash.htm");
    }

}
