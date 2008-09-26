package click;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClickContext {

    private final ServletConfig servletConfig;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private Page page;

    public ClickContext() {
        this.servletConfig = null;
        this.request = null;
        this.response = null;
    }

    public ClickContext(ServletConfig servletConfig, HttpServletRequest request, HttpServletResponse response) {
        this.servletConfig = servletConfig;
        this.request = request;
        this.response = response;
    }

    public Page getPage() {
        return this.page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

}
