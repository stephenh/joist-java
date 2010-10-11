package joist.web.pages.controls.form;

import joist.web.AbstractPage;
import joist.web.controls.form.Form;
import joist.web.controls.form.TextField;

import org.bindgen.Bindable;

@Bindable
public class TwoFormsPage extends AbstractPage {

  public Form form1 = new Form("form1");
  public Form form2 = new Form("form2");
  public String value1 = "foo";
  public String value2 = "foo";

  public void onInit() {
    TwoFormsPageBinding bind = new TwoFormsPageBinding(this);
    this.form1.add(new TextField(bind.value1()).id("value"));
    this.form2.add(new TextField(bind.value2()).id("value"));
  }

}
