package click.stages.render;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.exigencecorp.util.Log;

import click.ClickContext;
import click.CurrentContext;
import click.stages.Stage;
import click.util.Cast;

public class RenderStage implements Stage {

    private VelocityEngine engine;

    public void init() {
        this.engine = this.createVelocityEngine();
    }

    public void process() {
        Renderable page = Cast.orNull(Renderable.class, this.getContext().getPage());
        if (page == null) {
            return;
        }

        Log.debug("Calling onRender on {}", page);
        page.onRender();

        try {
            String path = "/" + page.getClass().getName().replace(".", "/").replace("Page", "") + ".htm";
            Log.debug("Rendering {} for {}", path, page);
            this.engine.mergeTemplate(path, new VelocityContext(page.getModel()), this.getContext().getResponse().getWriter());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected VelocityEngine createVelocityEngine() {
        VelocityEngine engine = new VelocityEngine();
        engine.setProperty("resource.loader", "class");
        engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        try {
            engine.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return engine;
    }

    private ClickContext getContext() {
        return CurrentContext.get();
    }

}
