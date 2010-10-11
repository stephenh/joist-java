package joist.web.pages.controls.form;

import joist.web.AbstractPage;
import joist.web.controls.form.Form;
import joist.web.controls.form.HiddenField;
import joist.web.controls.form.SubmitButton;
import joist.web.fakedomain.Employee;

import org.bindgen.Bindable;

@Bindable
public class HiddenFieldPage extends AbstractPage {

  public Form form = new Form("form");
  public Employee employee;

  public void onInit() {
    HiddenFieldPageBinding b = new HiddenFieldPageBinding(this);
    this.form.add(new HiddenField(b.employee()));
    this.form.add(new SubmitButton(b.submit()));
  }

  public void submit() {
  }

}
