package click.controls;

import java.util.ArrayList;
import java.util.List;

import click.Control;
import click.util.HtmlStringBuilderr;

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

    public String toString() {
        if (this.errors.size() == 0) {
            return "";
        }

        HtmlStringBuilderr sb = new HtmlStringBuilderr();
        sb.line("<div id={} class={}>");
        sb.line("<ul>");
        for (String error : this.errors) {
            sb.line("<li>{}</li>", error);
        }
        sb.line("</ul>");
        sb.line("</div>");
        return sb.toString();
    }

}
