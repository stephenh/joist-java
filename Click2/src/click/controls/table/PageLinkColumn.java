package click.controls.table;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.Copy;
import org.exigencecorp.util.Inflector;
import org.exigencecorp.util.StringBuilderr;

public class PageLinkColumn extends AbstractColumn {

    private final Class<?> pageClass;
    private final List<Object> params;
    private Binding<?> contentBinding;

    // private Object content;

    // Replace params with a annotation-gend XxxPageLink class?
    public PageLinkColumn(Class<?> pageClass, Object... params) {
        this.pageClass = pageClass;
        this.params = Copy.list(params);
    }

    @Override
    public void renderRow(StringBuilderr sb) {
        if (this.contentBinding != null) {
            sb.line("<a href=\"/{}.htm\">{}</a>", this.toPage(), this.contentBinding.get());
        }
    }

    private String toPage() {
        return StringUtils.lowerCase(StringUtils.removeEnd(this.pageClass.getSimpleName(), "Page"));
    }

    public PageLinkColumn setContent(Binding<?> binding) {
        this.contentBinding = binding;
        this.setLabel(Inflector.humanize(binding.getName()));
        return this;
    }

    public PageLinkColumn setContent(Object content) {
        // this.content = content;
        return this;
    }

    public String getName() {
        return this.contentBinding.getName();
    }

}
