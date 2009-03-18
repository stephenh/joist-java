package joist.web.controls;

import java.util.ArrayList;
import java.util.List;

import joist.web.Control;
import joist.web.util.HtmlWriter;


public class ErrorsDiv implements Control {

    private final List<String> errors = new ArrayList<String>();
    private String id;

    public ErrorsDiv() {
        this.id = "Errors";
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void onProcess() {
    }

    public void add(String error) {
        this.errors.add(error);
    }

    public void render(HtmlWriter w) {
        if (this.errors.size() == 0) {
            return;
        }
        w.line("<div id={} class={}>", this.getId(), "errors");
        w.line("<ul>");
        for (String error : this.errors) {
            w.line("<li>{}</li>", error);
        }
        w.line("</ul>");
        w.line("</div>");
    }

}
