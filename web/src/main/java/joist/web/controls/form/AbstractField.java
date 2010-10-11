package joist.web.controls.form;

import java.util.ArrayList;
import java.util.List;

import joist.converter.ConverterRegistry;
import joist.util.Inflector;
import joist.web.AbstractControl;
import joist.web.CurrentContext;
import joist.web.Page;
import joist.web.WebContext;

import org.bindgen.Binding;
import org.bindgen.ContainerBinding;

public abstract class AbstractField<T extends AbstractField<T>> extends AbstractControl<T> implements Field {

  private String label;
  private Binding<?> binding;

  protected AbstractField() {
  }

  protected AbstractField(Binding<?> binding) {
    this.id(binding.getName());
    this.setBinding(binding);
  }

  public Object getBoundValue() {
    if (this.binding == null) {
      return null;
    }
    return this.binding.get();
  }

  @Override
  public boolean isHidden() {
    return false;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void onProcess() {
    String value = this.getContext().getRequest().getParameter(this.getId());
    if (value == null && this.skipBindIfParameterIsNotPresent()) {
      return; // We would have at least gotten a "" if the field was really submitted
    }
    if (this.binding instanceof ContainerBinding) {
      // Look for List bindings
      String[] values = this.getContext().getRequest().getParameterValues(this.getId());
      List<Object> newValues = new ArrayList<Object>();
      for (String v : values) {
        Object converted = this.getProcessConverterRegistry().convert(v, ((ContainerBinding) this.binding).getContainedType());
        newValues.add(converted);
      }
      ((Binding<List>) this.binding).set(newValues);
    } else {
      Object converted = this.getProcessConverterRegistry().convert(value, this.binding.getType());
      // do this here or inside a Converter?
      if ("".equals(converted)) {
        converted = null;
      }
      ((Binding<Object>) this.binding).set(converted);
    }
  }

  /** @return by default the text converter for showing objects in text fields. */
  protected ConverterRegistry getProcessConverterRegistry() {
    return CurrentContext.get().getWebConfig().getTextConverterRegistry();
  }

  protected boolean skipBindIfParameterIsNotPresent() {
    return true;
  }

  public List<String> getErrors() {
    List<String> errors = new ArrayList<String>();
    return errors;
  }

  protected abstract T getThis();

  public T id(String id) {
    this.setId(id);
    this.setLabel(Inflector.humanize(id));
    return this.getThis();
  }

  public T label(String label) {
    this.setLabel(label);
    return this.getThis();
  }

  public String getLabel() {
    return this.label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  protected WebContext getContext() {
    return CurrentContext.get();
  }

  protected Page getPage() {
    return this.getContext().getPage();
  }

  public Binding<?> getBinding() {
    return this.binding;
  }

  public void setBinding(Binding<?> binding) {
    this.binding = binding;
  }

}
