package click.controls;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.Inflector;

import click.Control;
import click.CurrentContext;
import click.Page;
import click.util.HtmlStringBuilderr;

public class PageLink implements Control {

    private final Map<String, String> parameters = new HashMap<String, String>();
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
        if (value instanceof Binding) {
            value = ((Binding<?>) value).get();
        }
        for (Field field : this.pageClass.getFields()) {
            if (field.getType().isAssignableFrom(value.getClass())) {
                this.addParameter(field.getName(), value);
                return;
            }
        }
        throw new RuntimeException("Could not auto-bind value: " + value);
    }

    public void addParameter(String name, Object value) {
        if (value instanceof Binding) {
            value = ((Binding<?>) value).get();
        }
        String valueAsString = CurrentContext.get().getClickConfig().getUrlConverterRegistry().convert(value, String.class);
        this.parameters.put(name, valueAsString);
    }

    public String toString() {
        HtmlStringBuilderr sb = new HtmlStringBuilderr();
        sb.start("a");
        sb.attribute("id", this.getId());
        sb.attribute("href", this.getHref());
        sb.startDone();
        sb.append(this.getText());
        sb.end("a");
        return sb.toString();
    }

    public String getHref() {
        return this.getHrefWithoutContext();
    }

    public String getHrefWithoutContext() {
        String path = CurrentContext.get().getClickConfig().getPageResolver().getPathFromPage(this.pageClass.getName());
        if (this.parameters.size() > 0) {
            path += "?";
            for (Map.Entry<String, String> entry : this.parameters.entrySet()) {
                path += entry.getKey() + "=" + entry.getValue() + "&";
            }
        }
        return StringUtils.stripEnd(path, "?&"); // Strip last un-needed ? or &
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
