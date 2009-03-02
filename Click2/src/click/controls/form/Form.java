package click.controls.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.util.Inflector;
import org.exigencecorp.util.Log;

import click.ClickContext;
import click.Control;
import click.CurrentContext;
import click.util.HtmlStringBuilderr;

public class Form implements Control {

    private List<Field> fields = new ArrayList<Field>();
    private String id;
    private String heading;

    public Form(String id) {
        CurrentContext.addControlToCurrentPage(this);
        this.id = id;
        this.setHeading(Inflector.humanize(id));
    }

    public void onProcess() {
        String submittedFormName = this.getContext().getRequest().getParameter("_formId");
        if (submittedFormName == null || !StringUtils.equals(this.getId(), submittedFormName)) {
            Log.debug("{} != {}, skipping onProcess", "form", submittedFormName);
            return;
        }

        for (Field field : this.fields) {
            field.onProcess();
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
        sb.line("<input type=\"hidden\" name=\"_formId\" value={} />", this.getId());
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

    public String getId() {
        return this.id;
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
