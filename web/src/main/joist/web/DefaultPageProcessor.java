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

public class DefaultPageProcessor implements PageProcessor {

    public static final PageProcessor INSTANCE = new DefaultPageProcessor();

    public DefaultPageProcessor() {
    }

    @Override
    public void process(Page page) {
        Log.debug("Calling onInit on {}", page);
        this.doSetFieldsFromRequest(page);
        try {
            this.doInit(page);
            this.doAddControls(page);
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

    public void doInit(Page page) {
        Log.debug("Calling onInit on {}", page);
        page.onInit();
    }

    public void doAddControls(Page page) {
        for (Control c : CurrentContext.get().getAllControls()) {
            if (c.getParent() == null && c != page) {
                Log.debug("Adding {} to page {}", c, page);
                page.addControl(c);
            }
        }
    }

    public void doProcess(Page page) {
        Log.debug("Calling doProcess on {}", page);
        page.onProcess();
    }

    public void doAdaptControlsInModel(Page page) {
        for (Map.Entry<String, Object> e : CurrentContext.get().getModel().entrySet()) {
            if (e.getValue() instanceof Control) {
                e.setValue(new ControlRenderableAdapter((Control) e.getValue()));
            }
        }
    }

    public void doRender(Page page) {
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

    public void doResetFlash(Page page) {
        this.getContext().getFlash().clear();
    }

    public void doAddFieldsToModel(Page page) {
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

    public void doAddFlashToModel(Page page) {
        for (Entry<String, Object> e : CurrentContext.get().getFlash().get()) {
            CurrentContext.get().getModel().put(e.getKey(), e.getValue());
        }
    }

    public void doSetFieldsFromRequest(Page page) {
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

}
