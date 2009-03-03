package click.pages;

import org.exigencecorp.bindgen.Bindable;

import bindgen.click.pages.FlashPageBinding;
import click.AbstractPage;
import click.ClickKeywords;
import click.controls.form.Form;
import click.controls.form.SubmitField;

@Bindable
public class FlashPage extends AbstractPage {

    public Form form = new Form("form");

    @Override
    public void onInit() {
        FlashPageBinding b = new FlashPageBinding(this);
        this.form.add(new SubmitField(b.submit()));
    }

    public void submit() {
        ClickKeywords.flash("message", "success");
        ClickKeywords.redirect("/flash.htm");
    }

}
