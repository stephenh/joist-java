package joist.web.pages.controls;

import joist.web.AbstractContainer;
import joist.web.controls.form.TextField;
import joist.web.util.HtmlWriter;

public class WidgetControl extends AbstractContainer {

  public TextField firstName;
  public TextField lastName;

  public String getId() {
    return null;
  }

  public void render(HtmlWriter w) {
  }

}
