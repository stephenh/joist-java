package click.controls.table;

import org.apache.commons.lang.ObjectUtils;
import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.Inflector;

import click.util.HtmlWriter;

public class TextColumn extends AbstractColumn {

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

}
