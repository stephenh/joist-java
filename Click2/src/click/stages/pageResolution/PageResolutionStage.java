package click.stages.pageResolution;

import org.exigencecorp.util.Log;

import click.ClickContext;
import click.CurrentContext;
import click.Page;
import click.stages.Stage;
import click.util.ClassName;

public class PageResolutionStage implements Stage {

    private String basePackageName;

    public void init() {
    }

    @SuppressWarnings("unchecked")
    public void process() {
        // String uri = this.getContext().getRequest().getRequestURI(); --> /app/page.htm
        String uri = this.getContext().getRequest().getServletPath(); // --> /page.htm
        String className = this.getBasePackageName() + "." + ClassName.fromUri(uri) + "Page";
        Log.debug("Resolved {} => {}", uri, className);

        try {
            Class<Page> clazz = (Class<Page>) Class.forName(className);
            Page page = clazz.newInstance();
            this.getContext().setPage(page);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getBasePackageName() {
        return this.basePackageName;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    private ClickContext getContext() {
        return CurrentContext.get();
    }

}
