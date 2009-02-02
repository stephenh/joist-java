package click.stages.addFieldsToModel;

import java.lang.reflect.Field;

import org.exigencecorp.util.Log;

import click.ClickContext;
import click.CurrentContext;
import click.stages.Stage;
import click.stages.render.Renderable;
import click.util.Cast;

public class AddFieldsToModelStage implements Stage {

    public void init() {
    }

    public void process() {
        Renderable page = Cast.orNull(Renderable.class, this.getContext().getPage());
        if (page == null) {
            return;
        }

        try {
            for (Field field : page.getClass().getFields()) {
                Object value = field.get(page);
                if (value != null) {
                    Log.debug("Auto-adding field {}", field.getName());
                    page.addModel(field.getName(), value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ClickContext getContext() {
        return CurrentContext.get();
    }

}
