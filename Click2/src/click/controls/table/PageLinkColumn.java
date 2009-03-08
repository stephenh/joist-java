package click.controls.table;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.Inflector;

import click.Page;
import click.controls.PageLink;
import click.util.HtmlWriter;

public class PageLinkColumn extends AbstractColumn<PageLinkColumn> {

    private final PageLink pageLink;

    public PageLinkColumn(Class<? extends Page> pageClass) {
        super(StringUtils.removeEnd(pageClass.getSimpleName(), "Page")); // default id
        this.pageLink = new PageLink(pageClass);
    }

    @Override
    public void render(HtmlWriter sb) {
        this.pageLink.setId(this.getFullId() + ".link");
        this.pageLink.render(sb);
    }

    public PageLinkColumn param(Object param) {
        this.pageLink.param(param);
        return this;
    }

    public PageLinkColumn params(Object... params) {
        this.pageLink.params(params);
        return this;
    }

    public PageLinkColumn text(Binding<?> binding) {
        this.pageLink.text(binding);
        this.setLabel(Inflector.humanize(binding.getName()));
        return this;
    }

    public PageLinkColumn text(Object text) {
        this.pageLink.text(text);
        return this;
    }

    @Override
    protected PageLinkColumn getThis() {
        return this;
    }

}
