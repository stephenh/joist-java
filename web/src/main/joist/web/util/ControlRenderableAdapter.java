package joist.web.util;

import java.io.IOException;
import java.io.Writer;

import joist.web.Control;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.Renderable;

public class ControlRenderableAdapter implements Renderable {

    private final Control control;

    public ControlRenderableAdapter(Control control) {
        this.control = control;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer) throws IOException {
        this.control.render(new HtmlWriter(writer));
        return true;
    }

}
