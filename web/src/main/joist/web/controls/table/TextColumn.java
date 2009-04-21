package joist.web.controls.table;

import joist.util.Inflector;
import joist.web.util.HtmlWriter;

import org.apache.commons.lang.ObjectUtils;
import org.exigencecorp.bindgen.Binding;

public class TextColumn extends AbstractColumn<TextColumn> {

    private Binding<?> binding;

    public TextColumn(Binding<?> binding) {
        super(binding.getName());
        this.binding = binding;
        this.setLabel(Inflector.humanize(this.binding.getName()));
    }

    @Override
    public void render(HtmlWriter sb) {
        sb.append(ObjectUtils.toString(this.binding.get()));
    }

    public String getName() {
        return this.binding.getName();
    }

    @Override
    protected TextColumn getThis() {
        return this;
    }

}
