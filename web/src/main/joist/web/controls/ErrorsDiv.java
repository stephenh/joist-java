package joist.web.controls;

import java.util.ArrayList;
import java.util.List;

import joist.web.AbstractControl;
import joist.web.util.HtmlWriter;

public class ErrorsDiv extends AbstractControl {

    private final List<String> errors = new ArrayList<String>();

    public ErrorsDiv() {
        this.setId("errors");
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
        int i = 0;
        for (String error : this.errors) {
            w.line("<li id={}>{}</li>", (this.getId() + "." + (i++)), error);
        }
        w.line("</ul>");
        w.line("</div>");
    }

}
