package click.controls;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import click.Control;
import click.CurrentContext;
import click.util.HtmlStringBuilderr;

public class PageLink implements Control {

    private final Map<String, String> parameters = new HashMap<String, String>();
    private Class<?> pageClass;

    public String getId() {
        return null;
    }

    public void onProcess() {
    }

    public String toString() {
        HtmlStringBuilderr sb = new HtmlStringBuilderr();
        sb.start("a");
        sb.attribute("id", this.getId());
        sb.attribute("href", this.getHref());
        sb.startDone();
        sb.append("content");
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

}
