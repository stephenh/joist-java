package joist.web;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import joist.util.Log;
import joist.web.exceptions.IoException;
import joist.web.util.ControlRenderableAdapter;
import joist.web.util.HtmlWriter;

import org.apache.velocity.app.VelocityEngine;

public class DefaultPageProcessor implements PageProcessor {

    public static final PageProcessor INSTANCE = new DefaultPageProcessor();

    protected DefaultPageProcessor() {
    }

    public void process(Page page) {
        Log.debug("Calling onInit on {}", page);
        this.doFindFieldsThatAreControls(page);
        this.doSetFieldsFromRequest(page);
        try {
            this.doInit(page);
            this.doFindFieldsThatAreControls(page); // temporary hack to find fields set in init
            this.doProcess(page);
        } catch (RedirectException re) {
            try {
                CurrentContext.get().getResponse().sendRedirect(re.getUrl());
                return;
            } catch (IOException io) {
                throw new IoException(io);
            }
        }
        this.doAddFieldsToModel(page);
        this.doAddFlashToModel(page);
        this.doAdaptControlsInModel(page);
        this.doRender(page);
        this.doResetFlash(page);
    }

    protected void doFindFieldsThatAreControls(Page page) {
        try {
            for (Field field : page.getClass().getFields()) {
                Object value = field.get(page);
                if (value != null && value instanceof Control) {
                    Log.debug("Auto-adding field {} as control", field.getName());
                    page.addControl((Control) value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doInit(Page page) {
        Log.debug("Calling onInit on {}", page);
        page.onInit();
    }

    protected void doProcess(Page page) {
        for (Control control : page.getControls()) {
            Log.debug("Calling onProcess on {}", control.hashCode());
            control.onProcess();
        }
    }

    protected void doAdaptControlsInModel(Page page) {
        for (Map.Entry<String, Object> e : CurrentContext.get().getModel().entrySet()) {
            if (e.getValue() instanceof Control) {
                e.setValue(new ControlRenderableAdapter((Control) e.getValue()));
            }
        }
    }

    protected void doRender(Page page) {
        // Should be done by the page?
        this.getContext().getResponse().setContentType("text/html");
        Writer w;
        try {
            w = this.getContext().getResponse().getWriter();
        } catch (IOException io) {
            throw new IoException(io);
        }
        HtmlWriter hw = new HtmlWriter(w);
        page.render(hw);
        hw.close();
    }

    protected void doResetFlash(Page page) {
        this.getContext().getFlash().clear();
    }

    protected void doAddFieldsToModel(Page page) {
        try {
            for (Field field : page.getClass().getFields()) {
                Object value = field.get(page);
                if (value != null) {
                    Log.debug("Auto-adding field {} to model", field.getName());
                    CurrentContext.get().getModel().put(field.getName(), value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doAddFlashToModel(Page page) {
        for (Entry<String, Object> e : CurrentContext.get().getFlash().get()) {
            CurrentContext.get().getModel().put(e.getKey(), e.getValue());
        }
    }

    protected void doSetFieldsFromRequest(Page page) {
        try {
            // Auto-set request parameters into our page object
            for (Field field : page.getClass().getFields()) {
                String value = this.getContext().getRequest().getParameter(field.getName());
                if (value == null) {
                    Log.debug("No request parameter for {}", field.getName());
                } else {
                    Object converted = this.getContext().getClickConfig().getUrlConverterRegistry().convert(value, field.getType());
                    Log.debug("Setting {}.{} with request parameter {}", page, field.getName(), converted);
                    field.set(page, converted);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ClickContext getContext() {
        return CurrentContext.get();
    }

    protected VelocityEngine getVelocityEngine() {
        return this.getContext().getClickConfig().getVelocityEngine();
    }

    protected PageResolver getPageResolver() {
        return this.getContext().getClickConfig().getPageResolver();
    }

    protected Map<String, Object> getModel() {
        return this.getContext().getModel();
    }

}
