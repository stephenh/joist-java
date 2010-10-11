package joist.web;

import java.util.List;

public interface Page extends Control {

  /** Called immediately after the page is instantiated to get its processor. */
  PageProcessor getProcessor();

  void onInit();

  <T extends Control> T addControl(T c);

  List<Control> getControls();

  Control getLayout();

  boolean isAllowedViaUrl(Object converted);

}
