package click;

import click.util.HtmlWriter;

public interface Control {

    String getId();

    void onProcess();

    void render(HtmlWriter w);

}
