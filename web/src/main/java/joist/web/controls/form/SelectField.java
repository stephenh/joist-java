package joist.web.controls.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import joist.converter.ConverterRegistry;
import joist.util.Copy;
import joist.web.CurrentContext;
import joist.web.util.HtmlWriter;

import org.bindgen.Binding;
import org.bindgen.ContainerBinding;

public class SelectField<T> extends AbstractField<SelectField<T>> {

  private List<T> options = new ArrayList<T>();
  private Binding<List<T>> binding;
  private boolean showBlank = false;

  public SelectField(Binding<?> binding) {
    super(binding);
  }

  public SelectField<T> options(List<T> options) {
    this.options = options;
    return this;
  }

  public SelectField<T> options(T... options) {
    this.options = Copy.list(options);
    return this;
  }

  public SelectField<T> options(Binding<List<T>> binding) {
    this.binding = binding;
    return this;
  }

  public SelectField<T> showBlank() {
    this.showBlank = true;
    return this;
  }

  public SelectField<T> multiple(boolean multiple) {
    if (multiple) {
      this.getAttributes().put("multiple", "multiple");
    } else {
      this.getAttributes().remove("multiple");
    }
    return this;
  }

  public SelectField<T> size(int size) {
    this.getAttributes().put("size", Integer.toString(size));
    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void render(HtmlWriter w) {
    w.line("<select id={} name={}{}>", this.getFullId(), this.getId(), this.attributes);
    if (this.showBlank) {
      String blankId = this.getFullId() + "-blank";
      if (this.getBoundValue() == null) {
        w.line("<option id={} selected=\"selected\" value=\"\"></option>", blankId);
      } else {
        w.line("<option id={} value=\"\"></option>", blankId);
      }
    }
    int i = 0;
    for (T option : this.getOptionsPossiblyFromBinding()) {
      String id = this.getFullId() + "-" + i++;
      String forValue = CurrentContext.get().getWebConfig().getUrlConverterRegistry().convert(option, String.class);
      String forLabel = CurrentContext.get().getWebConfig().getTextConverterRegistry().convert(option, String.class);
      boolean selected = (this.getBinding() instanceof ContainerBinding) //
        ? ((Collection<Object>) this.getBoundValue()).contains(option) //
        : option.equals(this.getBoundValue());
      if (selected) {
        w.line("<option id={} selected=\"selected\" value={}>{}</option>", id, forValue, forLabel);
      } else {
        w.line("<option id={} value={}>{}</option>", id, forValue, forLabel);
      }
    }
    w.line("</select>");
  }

  protected List<T> getOptionsPossiblyFromBinding() {
    if (this.options.size() == 0 && this.binding != null) {
      this.options = this.binding.get();
    }
    return this.options;
  }

  @Override
  protected SelectField<T> getThis() {
    return this;
  }

  @Override
  protected ConverterRegistry getProcessConverterRegistry() {
    return CurrentContext.get().getWebConfig().getUrlConverterRegistry();
  }

}
