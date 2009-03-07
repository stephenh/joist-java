package click.util;

import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.Renderable;

import click.Control;

public class ControlRenderableAdapter implements Renderable {

    private final Control control;

    public ControlRenderableAdapter(Control control) {
        this.control = control;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer) {
        this.control.render(new HtmlWriter(writer));
        return true;
    }

}
