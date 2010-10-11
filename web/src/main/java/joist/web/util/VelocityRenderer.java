package joist.web.util;

import joist.util.Log;
import joist.web.Control;
import joist.web.CurrentContext;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityRenderer {

  public static void render(Control control, HtmlWriter w) {
    String template = CurrentContext.get().getWebConfig().getPageResolver().getTemplateFromPage(control.getClass().getName());
    Log.debug("Rendering {} for {}", template, control);
    try {
      VelocityEngine ve = CurrentContext.get().getWebConfig().getVelocityEngine();
      VelocityContext vc = new VelocityContext(CurrentContext.get().getModel());
      ve.mergeTemplate(template, "UTF-8", vc, w);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
