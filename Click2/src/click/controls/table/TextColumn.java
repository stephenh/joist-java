package click.controls.table;

import org.apache.commons.lang.ObjectUtils;
import org.exigencecorp.bindgen.Binding;
import org.exigencecorp.util.StringBuilderr;

public class TextColumn extends AbstractColumn {

    private Binding<?> binding;

    public TextColumn(Binding<?> binding) {
        this.binding = binding;
    }

    @Override
    public void renderHeader(StringBuilderr sb) {
        sb.append(this.binding.getName());
    }

    @Override
    public void renderRow(StringBuilderr sb) {
        sb.append(ObjectUtils.toString(this.binding.get()));
    }

    public String getName() {
        return this.binding.getName();
    }

}
