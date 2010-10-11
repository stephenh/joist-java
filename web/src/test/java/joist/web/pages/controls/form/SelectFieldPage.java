package joist.web.pages.controls.form;

import joist.util.Copy;
import joist.web.AbstractPage;
import joist.web.controls.form.Form;
import joist.web.controls.form.SelectField;
import joist.web.controls.form.SubmitButton;

import org.bindgen.Bindable;

@Bindable
public class SelectFieldPage extends AbstractPage {

  public Form form = new Form("form");
  public String value;
  public Boolean flipShowBlank = false;

  public void onInit() {
    SelectFieldPageBinding b = new SelectFieldPageBinding(this);
    SelectField<String> sf = new SelectField<String>(b.value());
    sf.options(Copy.list("one", "two"));
    if (this.flipShowBlank) {
      sf.showBlank();
    }
    this.form.add(sf);
    this.form.add(new SubmitButton(b.submit()));
  }

  public void submit() {
  }

}
