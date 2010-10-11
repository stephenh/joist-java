package joist.web.util;

import static joist.web.WebKeywords.getSession;
import static joist.web.WebKeywords.redirect;
import static joist.web.WebKeywords.setSession;

import java.util.List;

import joist.web.CurrentContext;
import joist.web.Page;

import org.bindgen.Binding;

public class SessionVariable<T> implements Binding<T> {

  private final Class<T> type;
  private final String name;
  private Class<? extends Page> redirectIfUnset;

  public static <T> SessionVariable<T> of(Class<T> type) {
    return new SessionVariable<T>(type, type.getName());
  }

  public static <T> SessionVariable<T> of(Class<T> type, String name) {
    return new SessionVariable<T>(type, name);
  }

  public SessionVariable(Class<T> type, String name) {
    this.type = type;
    this.name = name;
  }

  public SessionVariable<T> redirectIfUnset(Class<? extends Page> pageClass) {
    this.redirectIfUnset = pageClass;
    return this;
  }

  public T get() {
    String valueAsString = (String) getSession(this.name);
    if (valueAsString == null) {
      return null;
    }
    return CurrentContext.get().getWebConfig().getUrlConverterRegistry().convert(valueAsString, this.type);
  }

  public T getOrRedirect() {
    T value = this.get();
    if (value == null && this.redirectIfUnset != null) {
      redirect(this.redirectIfUnset);
    }
    return value;
  }

  public void set(T value) {
    String valueAsString = CurrentContext.get().getWebConfig().getUrlConverterRegistry().convert(value, String.class);
    setSession(this.name, valueAsString);
  }

  public void unset() {
    setSession(this.name, null);
  }

  public boolean isSet() {
    return this.get() != null;
  }

  @Override
  public Class<T> getType() {
    return this.type;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public List<Binding<?>> getChildBindings() {
    return null;
  }

  @Override
  public Binding<?> getParentBinding() {
    return null;
  }

  @Override
  public String getPath() {
    return null;
  }

  @Override
  public boolean getIsSafe() {
    return true;
  }

  @Override
  public T getSafely() {
    return this.get();
  }

}
