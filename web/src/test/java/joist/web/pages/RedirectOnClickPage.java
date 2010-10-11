package joist.web.pages;

import joist.util.TestCounter;
import joist.web.AbstractPage;
import joist.web.WebKeywords;
import joist.web.controls.form.Form;
import joist.web.controls.form.SubmitButton;
import joist.web.util.HtmlWriter;

import org.bindgen.Bindable;

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
  public void render(HtmlWriter w) {
    super.render(w);
    RedirectOnClickPage.rendered.next();
  }

  public void submit() {
    WebKeywords.redirect("/foo.htm");
    RedirectOnClickPage.lineAfterRedirect.next();
  }

}
