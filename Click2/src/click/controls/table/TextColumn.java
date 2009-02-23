package click.controls.table;

import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.StringBuilderr;

public class TextColumn extends AbstractColumn {

    private Binding<?> binding;

    public TextColumn(Binding<?> binding) {
        this.binding = binding;
    }

    @Override
    public void renderHeader(StringBuilderr sb) {
        sb.line("<th>{}</th>", this.binding.getName());
    }

    @Override
    public void renderRow(StringBuilderr sb) {
        sb.line("<td>{}</td>", this.binding.get());
    }

}
