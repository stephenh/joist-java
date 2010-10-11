package joist.web.pages;

import joist.web.AbstractPage;
import joist.web.RedirectException;

public class SetPublicFieldPage extends AbstractPage {

  public String value;

  @Override
  public boolean isAllowedViaUrl(Object converted) {
    if (converted instanceof String && ((String) converted).contains("foo")) {
      return true;
    }
    throw new RedirectException("/rejected");
  }

}
