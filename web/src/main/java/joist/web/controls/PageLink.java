package joist.web.controls;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import joist.util.Inflector;
import joist.web.AbstractControl;
import joist.web.CurrentContext;
import joist.web.Page;
import joist.web.util.HtmlWriter;
import joist.web.util.TextContent;

import org.apache.commons.lang.StringUtils;
import org.bindgen.Binding;

public class PageLink extends AbstractControl<PageLink> {

  private final Class<? extends Page> pageClass;
  private final Map<String, Object> parameters = new LinkedHashMap<String, Object>();
  private final TextContent text = new TextContent();

  public PageLink(Class<? extends Page> pageClass) {
    this.pageClass = pageClass;
    this.id(StringUtils.uncapitalize(StringUtils.removeEnd(pageClass.getSimpleName(), "Page")));
  }

  public static PageLink forCurrentPage() {
    Page page = CurrentContext.get().getPage();
    PageLink link = new PageLink(page.getClass());
    for (Entry<String, String[]> e : CurrentContext.get().getParameters().entrySet()) {
      link.param(e.getKey(), e.getValue()[0]);
    }
    return link;
  }

  public PageLink params(Object... values) {
    for (Object value : values) {
      this.param(value);
    }
    return this;
  }

  public PageLink param(Object value) {
    Class<?> valueType = (value instanceof Binding<?>) ? ((Binding<?>) value).getType() : value.getClass();
    for (Field field : this.pageClass.getFields()) {
      if (field.getType().isAssignableFrom(valueType)) {
        return this.param(field.getName(), value);
      }
    }
    throw new RuntimeException("Could not auto-bind param: " + value);
  }

  /**
   * @param name the key for the query string entry
   * @param value the value for the query string entry--will be converted to a string in <code>getHrefWithoutContext</code>
   */
  public PageLink param(String name, Object value) {
    this.parameters.put(name, value);
    return this;
  }

  public void render(HtmlWriter w) {
    w.start("a");
    w.attribute("id", this.getFullId());
    w.attribute("href", this.getHref());
    w.startDone();
    w.append(this.text.get());
    w.end("a");
  }

  public String getHref() {
    String contextPath = CurrentContext.get().getRequest().getContextPath();
    return contextPath + this.getHrefWithoutContext();
  }

  public String getHrefWithoutContext() {
    String path = CurrentContext.get().getWebConfig().getPageResolver().getPathFromPage(this.pageClass.getName());
    path += this.getQueryString();
    return path;
  }

  protected String getQueryString() {
    String qs = "";
    if (this.parameters.size() > 0) {
      qs += "?";
      for (Map.Entry<String, Object> entry : this.parameters.entrySet()) {
        Object value = entry.getValue();
        // We delay evaluating bindings until here in case they have changed since addParameter was called, e.g. in Tables on each row
        if (value instanceof Binding<?>) {
          value = ((Binding<?>) value).get();
        }
        String valueAsString = CurrentContext.get().getWebConfig().getUrlConverterRegistry().convert(value, String.class);
        qs += entry.getKey() + "=" + valueAsString + "&";
      }
      qs = StringUtils.stripEnd(qs, "&"); // Strip last &
    }
    return qs;
  }

  public PageLink text(Object text) {
    this.text.set(text);
    return this;
  }

  public PageLink id(String id) {
    this.setId(id);
    this.text(Inflector.humanize(id));
    return this;
  }

  protected PageLink getThis() {
    return this;
  }

}
