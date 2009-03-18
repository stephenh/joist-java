package joist.web;

import joist.web.util.HtmlWriter;

public interface Control {

    String getId();

    void onProcess();

    void render(HtmlWriter w);

}
