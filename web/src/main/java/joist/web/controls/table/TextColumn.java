package joist.web.controls.table;

import joist.util.Inflector;
import joist.web.CurrentContext;
import joist.web.util.HtmlWriter;

import org.bindgen.Binding;

public class TextColumn extends AbstractColumn<TextColumn> {

  private Binding<?> binding;

  public TextColumn(Binding<?> binding) {
    super(binding.getName());
    this.binding = binding;
    this.setLabel(Inflector.humanize(this.binding.getName()));
  }

  @Override
  public void render(HtmlWriter sb) {
    String asText = CurrentContext.get().getWebConfig().getTextConverterRegistry().convert(this.binding.get(), String.class);
    sb.append(asText);
  }

  public String getName() {
    return this.binding.getName();
  }

  @Override
  protected TextColumn getThis() {
    return this;
  }

}
