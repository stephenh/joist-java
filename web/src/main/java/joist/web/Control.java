package joist.web;

import joist.web.util.HtmlWriter;

public interface Control {

  String getId();

  String getFullId();

  void onProcess();

  void onRender();

  void render(HtmlWriter w);

  Control getParent();

  void setParent(Control c);

}
