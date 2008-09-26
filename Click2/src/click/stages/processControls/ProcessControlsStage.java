package click.stages.processControls;

import org.exigencecorp.util.Log;

import click.ClickContext;
import click.CurrentContext;
import click.controls.Control;
import click.stages.Stage;
import click.util.Cast;

public class ProcessControlsStage implements Stage {

    public void init() {
    }

    public void process() {
        Processable page = Cast.orNull(Processable.class, this.getContext().getPage());
        if (page == null) {
            return;
        }

        for (Control control : page.getControls()) {
            Log.debug("Calling onProcess on {}", control.hashCode());
            control.onProcess();
        }
    }

    private ClickContext getContext() {
        return CurrentContext.get();
    }

}
