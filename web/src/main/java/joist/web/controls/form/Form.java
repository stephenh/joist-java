package joist.web.controls.form;

import java.util.ArrayList;
import java.util.List;

import joist.util.Inflector;
import joist.util.Join;
import joist.util.Log;
import joist.web.AbstractControl;
import joist.web.CurrentContext;
import joist.web.WebContext;
import joist.web.util.HtmlWriter;

import org.apache.commons.lang.StringUtils;

public class Form extends AbstractControl<Form> {

  private final List<Field> fields = new ArrayList<Field>();
  private final List<Button> buttons = new ArrayList<Button>();
  private String heading;

  public Form(String id) {
    this.id(id);
  }

  @Override
  public void onProcess() {
    // Should perhaps send the onProcess() through and let Fields/Buttons note that
    // we're not "POSTing"--spreads out the logic, but I think it would be cleaner
    String submittedFormName = this.getContext().getRequest().getParameter("_formId");
    if (submittedFormName == null || !StringUtils.equals(this.getId(), submittedFormName)) {
      Log.trace("{} != {}, skipping onProcess", submittedFormName, this.getId());
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
    this.setHeading(Inflector.humanize(StringUtils.removeEnd(id, "Form")));
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
    if (this.buttons.size() == 0) {
      button.setDefaultButton(true);
    }
    this.buttons.add(button);
    button.setParent(this);
  }

  @Override
  public void render(HtmlWriter w) {
    if (this.fields.size() == 0 && this.buttons.size() == 0) {
      return;
    }
    this.renderStartTags(w);
    this.renderHeadingTags(w);
    this.renderFields(w);
    this.renderEndTags(w);
  }

  protected void renderStartTags(HtmlWriter w) {
    w.line("<div class=\"web-form\">");
    w.line("<form method=\"post\">");
    w.line("<input type=\"hidden\" name=\"_formId\" value={} />", this.getId());
    for (Field field : this.fields) {
      if (field.isHidden()) {
        w.line("{}", field);
      }
    }
  }

  protected void renderHeadingTags(HtmlWriter w) {
    w.line("<h2>{}</h2>", this.getHeading());
  }

  protected void renderFields(HtmlWriter w) {
    w.line("<table>");
    // Fields
    for (Field field : this.fields) {
      if (!field.isHidden()) {
        w.line("<tr>");
        w.line("<th>{}</th>", field.getLabel());
        w.line("<td>{}</td>", field);
        w.line("<td>{}</td>", StringUtils.defaultIfEmpty(Join.join(field.getErrors(), "<br/>"), "&nbsp;"));
        w.line("</tr>");
      }
    }
    // Buttons
    if (this.buttons.size() > 0) {
      w.line("<tr>");
      w.line("<th>&nbsp;</th>");
      w.line("<td>");
      for (Button button : this.buttons) {
        w.line("{}", button);
      }
      w.line("</td>");
      w.line("<td>&nbsp;</td>");
      w.line("</tr>");
    }
    w.line("</table>");
  }

  protected void renderEndTags(HtmlWriter w) {
    w.line("</form>");
    w.line("</div>");
  }

  public String getHeading() {
    return this.heading;
  }

  public void setHeading(String heading) {
    this.heading = heading;
  }

  private WebContext getContext() {
    return CurrentContext.get();
  }

  protected Form getThis() {
    return this;
  }

}
