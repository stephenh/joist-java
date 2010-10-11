package joist.web.pages;

import joist.web.AbstractPage;
import joist.web.controls.form.Form;
import joist.web.controls.form.TextField;

public class AddModelAllControlsPage extends AbstractPage {

  public Boolean onlyField = false;

  public void onInit() {
    Form f = new Form("foo");
    f.add(new TextField().id("bar"));
  }

}
