package click;

import click.controls.Control;
import click.stages.processControls.Processable;
import click.util.Cast;

public class CurrentContext {

    private static final ThreadLocal<ClickContext> CURRENT = new ThreadLocal();

    public static ClickContext get() {
        return CurrentContext.CURRENT.get();
    }

    public static void set(ClickContext context) {
        CurrentContext.CURRENT.set(context);
    }

    public static void addControlToCurrentPage(Control control) {
        Processable page = Cast.orNull(Processable.class, CurrentContext.get().getPage());
        if (page != null) {
            page.addControl(control);
        }
    }

}
