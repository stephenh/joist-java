package click.controls;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.Inflector;

import click.Control;
import click.CurrentContext;
import click.Page;
import click.util.HtmlWriter;

public class PageLink implements Control {

    private final Map<String, Object> parameters = new LinkedHashMap<String, Object>();
    private Class<? extends Page> pageClass;
    private String id;
    private String text;

    public PageLink(Class<? extends Page> pageClass) {
        this.pageClass = pageClass;
        this.setId(StringUtils.removeEnd(pageClass.getSimpleName(), "Page"));
        this.setText(Inflector.humanize(StringUtils.removeEnd(pageClass.getSimpleName(), "Page")));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void onProcess() {
    }

    public void addParameter(Object value) {
        Class<?> valueType = (value instanceof Binding) ? ((Binding<?>) value).getType() : value.getClass();
        for (Field field : this.pageClass.getFields()) {
            if (field.getType().isAssignableFrom(valueType)) {
                this.addParameter(field.getName(), value);
                return;
            }
        }
        throw new RuntimeException("Could not auto-bind value: " + value);
    }

    /**
     * @param name the key for the query string entry
     * @param value the value for the query string entry--will be converted to a string in <code>getHrefWithoutContext</code>
     */
    public void addParameter(String name, Object value) {
        this.parameters.put(name, value);
    }

    public void render(HtmlWriter w) {
        w.start("a");
        w.attribute("id", this.getId());
        w.attribute("href", this.getHref());
        w.startDone();
        w.append(this.getText());
        w.end("a");
    }

    public String getHref() {
        return this.getHrefWithoutContext();
    }

    public String getHrefWithoutContext() {
        String path = CurrentContext.get().getClickConfig().getPageResolver().getPathFromPage(this.pageClass.getName());
        if (this.parameters.size() > 0) {
            path += "?";
            for (Map.Entry<String, Object> entry : this.parameters.entrySet()) {
                Object value = entry.getValue();
                // We delay evaluating bindings until here in case they have changed since addParameter was called, e.g. in Tables on each row
                if (value instanceof Binding) {
                    value = ((Binding<?>) value).get();
                }
                String valueAsString = CurrentContext.get().getClickConfig().getUrlConverterRegistry().convert(value, String.class);
                path += entry.getKey() + "=" + valueAsString + "&";
            }
            path = StringUtils.stripEnd(path, "&"); // Strip last &
        }
        return path;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
