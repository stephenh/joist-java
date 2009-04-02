package joist.web.controls.form;

import java.util.ArrayList;
import java.util.List;

import joist.util.Inflector;
import joist.util.Join;
import joist.util.Log;
import joist.web.AbstractControl;
import joist.web.ClickContext;
import joist.web.CurrentContext;
import joist.web.util.HtmlWriter;

import org.apache.commons.lang.StringUtils;

public class Form extends AbstractControl {

    private final List<Field> fields = new ArrayList<Field>();
    private final List<Button> buttons = new ArrayList<Button>();
    private String heading;

    public Form(String id) {
        this.id(id);
    }

    @Override
    public void onProcess() {
        String submittedFormName = this.getContext().getRequest().getParameter("_formId");
        if (submittedFormName == null || !StringUtils.equals(this.getId(), submittedFormName)) {
            Log.debug("{} != {}, skipping onProcess", "form", submittedFormName);
            return;
        }
        for (Field field : this.fields) {
            field.onProcess();
        }
        for (Button button : this.buttons) {
            button.onProcess();
        }
    }

    @Override
    public void onRender() {
        for (Field field : this.fields) {
            field.onRender();
        }
        for (Button button : this.buttons) {
            button.onRender();
        }
    }

    public Form id(String id) {
        this.setId(id);
        this.setHeading(Inflector.humanize(id));
        return this;
    }

    public Form heading(String heading) {
        this.setHeading(heading);
        return this;
    }

    public void add(Field field) {
        this.fields.add(field);
        field.setParent(this);
    }

    public void add(Button button) {
        this.buttons.add(button);
        button.setParent(this);
    }

    @Override
    public void render(HtmlWriter w) {
        this.renderStartTags(w);
        this.renderHeadingTags(w);
        this.renderFields(w);
        this.renderEndTags(w);
    }

    protected void renderStartTags(HtmlWriter w) {
        w.line("<form method=\"post\">");
        w.line("<input type=\"hidden\" name=\"_formId\" value={} />", this.getId());
    }

    protected void renderHeadingTags(HtmlWriter w) {
        w.line("<p class={}>{}</p>", "clickFormHeading", this.getHeading());
    }

    protected void renderFields(HtmlWriter w) {
        w.line("<table class={}>", "clickForm");
        // Fields
        for (Field field : this.fields) {
            w.line("<tr>");
            w.line("<th>{}</th>", field.getLabel());
            w.line("<td>{}</td>", field);
            w.line("<td>{}</td>", StringUtils.defaultIfEmpty(Join.join(field.getErrors(), "<br/>"), "&nbsp;"));
            w.line("</tr>");
        }
        // Buttons
        if (this.buttons.size() > 0) {
            w.line("<tr><th>&nbsp;</th><td>");
            for (Button button : this.buttons) {
                w.line("{}", button);
            }
            w.line("</td><td>&nbsp;</td></tr>");
        }
        w.line("</table>");
    }

    protected void renderEndTags(HtmlWriter w) {
        w.line("</form>");
    }

    public String getHeading() {
        return this.heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    private ClickContext getContext() {
        return CurrentContext.get();
    }

}
