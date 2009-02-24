package click;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClickContext {

    private final ServletConfig servletConfig;
    private final ClickConfig clickConfig;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Map<String, Object> model = new HashMap<String, Object>();
    private Page page;

    public ClickContext() {
        this.servletConfig = null;
        this.clickConfig = null;
        this.request = null;
        this.response = null;
    }

    public ClickContext(ServletConfig servletConfig, ClickConfig clickConfig, HttpServletRequest request, HttpServletResponse response) {
        this.servletConfig = servletConfig;
        this.clickConfig = clickConfig;
        this.request = request;
        this.response = response;
    }

    public Page getPage() {
        return this.page;
    }

    public void setPage(Page page) {
        if (this.page != null) {
            throw new RuntimeException("No changing the page after its been set--forwarding is evil, redirect instead.");
        }
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

    public ClickConfig getClickConfig() {
        return this.clickConfig;
    }

    public Map<String, Object> getModel() {
        return this.model;
    }

}
