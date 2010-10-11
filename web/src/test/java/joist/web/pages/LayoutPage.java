package joist.web.pages;

import joist.web.AbstractLayoutControl;
import joist.web.AbstractPage;
import joist.web.Control;

public class LayoutPage extends AbstractPage {

  @Override
  public Control getLayout() {
    return new Wrapper();
  }

  public static class Wrapper extends AbstractLayoutControl {
  }

}
