package click.stages.setFieldsFromRequest;

import java.lang.reflect.Field;

import org.exigencecorp.util.Log;
import org.mvel.PropertyAccessor;

import click.ClickContext;
import click.CurrentContext;
import click.Page;
import click.stages.Stage;

public class SetFieldsFromRequestStage implements Stage {

    public void init() {
    }

    public void process() {
        Page page = this.getContext().getPage();
        if (page == null) {
            return;
        }

        // Auto-set request parameters into our page object
        for (Field field : page.getClass().getFields()) {
            String value = this.getContext().getRequest().getParameter(field.getName());
            if (value == null) {
                Log.debug("No request parameter for {}", field.getName());
            } else {
                Log.debug("Setting {}.{} with request parameter {}", new Object[] { page, field.getName(), value });
                PropertyAccessor.set(page, field.getName(), value);
            }
        }
    }

    private ClickContext getContext() {
        return CurrentContext.get();
    }

}
