package joist.web.pages;

import joist.web.AbstractPage;
import joist.web.WebKeywords;
import joist.web.controls.form.Form;
import joist.web.controls.form.SubmitButton;

import org.bindgen.Bindable;

@Bindable
public class FlashPage extends AbstractPage {

  public Form form = new Form("form");

  @Override
  public void onInit() {
    FlashPageBinding b = new FlashPageBinding(this);
    this.form.add(new SubmitButton(b.submit()));
  }

  public void submit() {
    WebKeywords.flash("message", "success");
    WebKeywords.redirect("/flash.htm");
  }

}
