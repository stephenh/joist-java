package click.controls.form;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.util.Log;

import click.ClickContext;
import click.Control;
import click.CurrentContext;
import click.util.HtmlStringBuilderr;

public class Form implements Control {

    private List<Field> fields = new ArrayList();
    private String heading;

    public Form() {
        CurrentContext.addControlToCurrentPage(this);
    }

    public String getId() {
        return null;
    }

    public Form(String heading) {
        this();
        this.setHeading(heading);
    }

    public void onProcess() {
        String submittedFormName = this.getContext().getRequest().getParameter("form_name");
        if (!"form".equals(submittedFormName)) {
            Log.debug("{} != {}, skipping onProcess", "form", submittedFormName);
            return;
        }

        for (Field field : this.fields) {
            String value = this.getContext().getRequest().getParameter(field.getName());
            field.bindRequestValue(value);
        }
    }

    public void add(Field field) {
        this.fields.add(field);
    }

    public String toString() {
        HtmlStringBuilderr sb = new HtmlStringBuilderr();
        this.renderStartTags(sb);
        this.renderHeadingTags(sb);
        this.renderFields(sb);
        this.renderEndTags(sb);
        return sb.toString();
    }

    protected void renderStartTags(HtmlStringBuilderr sb) {
        sb.line("<form>");
    }

    protected void renderHeadingTags(HtmlStringBuilderr sb) {
        sb.line("<p class={}>{}</p>", "clickFormHeading", this.getHeading());
    }

    protected void renderFields(HtmlStringBuilderr sb) {
        sb.line("<table class={}>", "clickForm");
        for (Field field : this.fields) {
            sb.line("<tr>");
            sb.line("<th>{}</th>", field.getLabel());
            sb.line("<td>{}</td>", field.toString());
            sb.line("</tr>");
        }
        sb.line("</table>");
    }

    protected void renderEndTags(HtmlStringBuilderr sb) {
        sb.line("</form>");
    }

    public String getHeading() {
        return this.heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void onSubmit(Runnable runnable) {
    }

    private ClickContext getContext() {
        return CurrentContext.get();
    }

}
