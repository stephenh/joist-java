package click;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.exigencecorp.util.Log;

import click.stages.Stage;

public class ClickServlet extends HttpServlet {

    private static final long serialVersionUID = 1;
    private ClickConfig clickConfig;
    private ServletConfig servletConfig;

    public ClickServlet() {
        this.clickConfig = this.createClickConfig();
    }

    public ClickServlet(ClickConfig clickConfig) {
        this.clickConfig = clickConfig;
    }

    @Override
    public void init(ServletConfig servletConfig) {
        this.servletConfig = servletConfig;
        for (Stage stage : this.clickConfig.getStages()) {
            Log.debug("Initializing {}", stage);
            stage.init();
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ClickContext context = new ClickContext(this.servletConfig, request, response);
        CurrentContext.set(context);
        for (Stage stage : this.clickConfig.getStages()) {
            Log.debug("Processing {} in {}", request.getRequestURI(), stage);
            stage.process();
        }
    }

    protected ClickConfig createClickConfig() {
        return new ClickConfig();
    }

}
