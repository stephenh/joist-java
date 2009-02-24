package click;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ClickContext context = new ClickContext(this.servletConfig, this.clickConfig, request, response);
        CurrentContext.set(context);

        // Get the path, e.g. /page.htm (without the webapp context)
        String path = context.getRequest().getServletPath();
        String className = context.getClickConfig().getPageResolver().getPageFromPath(path);

        try {
            Class<Page> clazz = (Class<Page>) Class.forName(className);
            Page page = clazz.newInstance();
            context.setPage(page);

            PageProcessor processor = page.getProcessor();
            processor.process(page);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ClickConfig createClickConfig() {
        return new ClickConfig("yourapp.pages");
    }

}
