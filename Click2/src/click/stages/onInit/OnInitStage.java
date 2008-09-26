package click.stages.onInit;

import org.exigencecorp.util.Log;

import click.ClickContext;
import click.CurrentContext;
import click.stages.Stage;
import click.util.Cast;

public class OnInitStage implements Stage {

    public void init() {
    }

    public void process() {
        Initable page = Cast.orNull(Initable.class, this.getContext().getPage());
        if (page == null) {
            return;
        }

        Log.debug("Calling onInit on {}", page);
        page.onInit();
    }

    private ClickContext getContext() {
        return CurrentContext.get();
    }

}
