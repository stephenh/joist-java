package click.controls.table;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.StringBuilderr;

public class PageLinkColumn extends AbstractColumn {

    private Class pageClass;
    private Binding<?> contentBinding;

    // private Object content;

    // Replace params with a annotation-gend XxxPageLink class?
    public PageLinkColumn(Class pageClass, Object... params) {
        this.pageClass = pageClass;
    }

    @Override
    public void renderHeader(StringBuilderr sb) {
        if (this.contentBinding != null) {
            sb.line("<th>{}</th>", this.contentBinding.getName());
        }
    }

    @Override
    public void renderRow(StringBuilderr sb) {
        if (this.contentBinding != null) {
            sb.line("<td><a href=\"/{}.htm\">{}</a></td>", this.toPage(), this.contentBinding.get());
        }
    }

    private String toPage() {
        return StringUtils.lowerCase(StringUtils.removeEnd(this.pageClass.getSimpleName(), "Page"));
    }

    public PageLinkColumn setContent(Binding<?> binding) {
        this.contentBinding = binding;
        return this;
    }

    public PageLinkColumn setContent(Object content) {
        // this.content = content;
        return this;
    }

}
