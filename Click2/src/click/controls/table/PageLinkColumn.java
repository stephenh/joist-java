package click.controls.table;

import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.Inflector;

import click.Page;
import click.controls.PageLink;
import click.util.HtmlWriter;

public class PageLinkColumn extends AbstractColumn {

    private final PageLink pageLink;

    public PageLinkColumn(Class<? extends Page> pageClass, Object... params) {
        this.pageLink = new PageLink(pageClass);
        for (Object o : params) {
            this.pageLink.addParameter(o);
        }
    }

    @Override
    public void renderRow(HtmlWriter sb) {
        this.pageLink.render(sb);
    }

    public PageLinkColumn setContent(Binding<?> binding) {
        this.pageLink.setText(binding.toString());
        this.setLabel(Inflector.humanize(binding.getName()));
        return this;
    }

    public PageLinkColumn setContent(Object content) {
        // this.content = content;
        return this;
    }

    public String getName() {
        return null;
    }

}
