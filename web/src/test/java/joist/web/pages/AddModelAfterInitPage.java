package joist.web.pages;

import joist.web.AbstractPage;

public class AddModelAfterInitPage extends AbstractPage {

  public String value;

  @Override
  public void onInit() {
    this.value = "init";
  }

}
