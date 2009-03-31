package joist.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Map.Entry;

import joist.util.Log;
import joist.web.exceptions.IoException;
import joist.web.util.HtmlWriter;

public class DefaultPageProcessor implements PageProcessor {

    public static final PageProcessor INSTANCE = new DefaultPageProcessor();

    @Override
    public void process(Page page) {
        try {
            this.doSetFieldsFromRequest(page);
            this.doOnInit(page);
            this.doAddOrphanControlsToPage(page);
            this.doProcess(page);
            this.doOnRender(page);
        } catch (RedirectException re) {
            this.doRedirect(re);
            return;
        } catch (RenderException re) {
            this.doRender(page, re);
            return;
        }
        this.doAddAllControlsToModel(page);
        this.doAddFieldsToModel(page);
        this.doAddFlashToModel(page);
        this.doRender(page);
        this.doResetFlash(page);
    }

    public void doSetFieldsFromRequest(Page page) {
        try {
            // Auto-set request parameters into our page object
            for (Field field : page.getClass().getFields()) {
                String value = this.getContext().getRequest().getParameter(field.getName());
                if (value != null) {
                    Object converted = this.getContext().getClickConfig().getUrlConverterRegistry().convert(value, field.getType());
                    if (page.isAllowedViaUrl(converted)) {
                        Log.debug("Setting {}.{} to {}", page, field.getName(), value);
                        field.set(page, converted);
                    } else {
                        Log.debug("Skipping {}.{} to {}", page, field.getName(), value);
                    }
                }
            }
        } catch (IllegalAccessException iae) {
            throw new RuntimeException(iae);
        }
    }

    public void doOnInit(Page page) {
        Log.debug("Calling onInit on {}", page);
        page.onInit();
    }

    public void doAddOrphanControlsToPage(Page page) {
        for (Control c : CurrentContext.get().getAllControls()) {
            if (c.getParent() == null && c != page) {
                Log.debug("Adding orphan control {} to page {}", c, page);
                page.addControl(c);
            }
        }
    }

    public void doProcess(Page page) {
        Log.debug("Calling doProcess on {}", page);
        page.onProcess();
    }

    public void doRedirect(RedirectException re) {
        try {
            CurrentContext.get().getResponse().sendRedirect(re.getUrl());
        } catch (IOException io) {
            throw new IoException(io);
        }
    }

    public void doAddAllControlsToModel(Page page) {
        for (Control c : CurrentContext.get().getAllControls()) {
            Log.debug("Adding control {} to model", c.getFullId());
            CurrentContext.get().getModel().put(c.getFullId(), c);
        }
    }

    public void doAddFieldsToModel(Page page) {
        try {
            for (Field field : page.getClass().getFields()) {
                Object value = field.get(page);
                if (value != null) {
                    Log.debug("Adding field {} to model", field.getName());
                    CurrentContext.get().getModel().put(field.getName(), value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void doAddFlashToModel(Page page) {
        for (Entry<String, Object> e : CurrentContext.get().getFlash().entrySet()) {
            CurrentContext.get().getModel().put(e.getKey(), e.getValue());
        }
    }

    public void doOnRender(Page page) {
        Log.debug("Calling onRender on {}", page);
        page.onRender();
    }

    public void doRender(Page page) {
        this.getContext().getResponse().setContentType("text/html");
        HtmlWriter w = new HtmlWriter(this.getWriter());
        page.getLayout().render(w);
        w.close();
    }

    public void doRender(Page page, RenderException re) {
        this.getContext().getResponse().setContentType(re.getContentType());
        try {
            OutputStream out = this.getContext().getResponse().getOutputStream();
            out.write(re.getBytes());
            out.flush();
        } catch (IOException io) {
            throw new IoException(io);
        }
    }

    public void doResetFlash(Page page) {
        this.getContext().getFlash().clear();
    }

    protected ClickContext getContext() {
        return CurrentContext.get();
    }

    protected Writer getWriter() {
        try {
            return this.getContext().getResponse().getWriter();
        } catch (IOException io) {
            throw new IoException(io);
        }
    }

}
