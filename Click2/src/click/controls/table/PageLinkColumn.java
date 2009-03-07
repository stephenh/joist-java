package click.controls.table;

import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.Inflector;

import click.Page;
import click.controls.PageLink;
import click.util.HtmlWriter;

public class PageLinkColumn extends AbstractColumn {

    private final PageLink pageLink;

    public PageLinkColumn(Class<? extends Page> pageClass) {
        super(pageClass.getSimpleName()); // default id
        this.pageLink = new PageLink(pageClass);
    }

    @Override
    public void render(HtmlWriter sb) {
        this.pageLink.render(sb);
    }

    public PageLinkColumn param(Object param) {
        this.pageLink.addParameter(param);
        return this;
    }

    public PageLinkColumn params(Object... params) {
        for (Object param : params) {
            this.pageLink.addParameter(param);
        }
        return this;
    }

    public PageLinkColumn setText(Binding<?> binding) {
        this.pageLink.setText(binding);
        this.setLabel(Inflector.humanize(binding.getName()));
        return this;
    }

    public PageLinkColumn setText(Object text) {
        this.pageLink.setText(text);
        return this;
    }

}
