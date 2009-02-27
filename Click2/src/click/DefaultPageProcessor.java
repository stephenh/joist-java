package click;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.exigencecorp.util.Log;

public class DefaultPageProcessor implements PageProcessor {

    public static final PageProcessor INSTANCE = new DefaultPageProcessor();

    protected DefaultPageProcessor() {
    }

    public void process(Page page) {
        Log.debug("Calling onInit on {}", page);
        this.doFindFieldsThatAreControls(page);
        this.doSetFieldsFromRequest(page);
        this.doInit(page);
        this.doProcess(page);
        this.doAddFieldsToModel(page);
        this.doRender(page);
    }

    protected void doFindFieldsThatAreControls(Page page) {
        try {
            for (Field field : page.getClass().getFields()) {
                Object value = field.get(page);
                if (value != null && value instanceof Control) {
                    Log.debug("Auto-adding field {}", field.getName());
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

    protected void doRender(Page page) {
        Log.debug("Calling onRender on {}", page);
        boolean okayToContinue = page.onRender();
        if (!okayToContinue) {
            return;
        }
        try {
            String template = this.getPageResolver().getTemplateFromPage(page.getClass().getName());

            Log.debug("Rendering {} for {}", template, page);
            this.getVelocityEngine().mergeTemplate(//
                template,
                new VelocityContext(this.getModel()),
                this.getContext().getResponse().getWriter());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doAddFieldsToModel(Page page) {
        try {
            for (Field field : page.getClass().getFields()) {
                Object value = field.get(page);
                if (value != null) {
                    Log.debug("Auto-adding field {}", field.getName());
                    CurrentContext.get().getModel().put(field.getName(), value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
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
                    Log.debug("Setting {}.{} with request parameter {}", page, field.getName(), value);
                    // TODO: Add type conversion
                    field.set(page, value);
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
