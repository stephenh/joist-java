package joist.domain.builders;

import java.util.HashMap;
import java.util.Map;

import joist.domain.DomainObject;

/**
 * Tracks the entities we're currently using within a {@code .defaults()} call.
 *
 * The idea is that schemas are generally a DAG, and so if we've made a parent of
 * one type, * it is likely any other defaults for that same type (e.g. Parent.class)
 * that we come across (within the context of a {@code .defaults()} call, should use
 * the same instance.
 *
 * To do this, we can use a thread-local variable to track "what entity is currently
 * the default for this type or that type".
 */
public class DefaultsContext {

  private static final ThreadLocal<DefaultsContext> currentContext = new ThreadLocal<DefaultsContext>();

  /** How level defaults we're recursed into .defaults calls. */
  private int level;

  /** The current default for a given type. */
  private Map<Class<?>, Object> defaults = new HashMap<Class<?>, Object>();

  /** Called to pre-queue a potential default before we actually start the .defaults() logic. */
  public static void use(AbstractBuilder<? extends DomainObject> builder) {
    get().rememberIfSet(builder);
  }

  /** Called when a .defaults call begins. */
  public static DefaultsContext push() {
    DefaultsContext c = get();
    c.level++;
    return c;
  }

  private static DefaultsContext get() {
    DefaultsContext c = currentContext.get();
    if (c == null) {
      c = new DefaultsContext();
      currentContext.set(c);
    }
    return c;
  }

  /** Called when a .defaults call ends. */
  public void pop() {
    this.level--;
    if (this.level == 0) {
      // we're leaving the original .defaults() call, so forget this state
      currentContext.set(null);
    }
  }

  /** Remembers a potentially-set value, e.g. if the user had already set one before calling .defaults(). */
  public void rememberIfSet(AbstractBuilder<? extends DomainObject> builder) {
    if (builder != null) {
      // Eventually use the root alias? Not sure...
      this.defaults.put(builder.get().getClass(), builder.get());
    }
  }

  /** Returns the instance for {@code type}, assuming we've seen one. */
  public <T> T getIfAvailable(Class<T> type) {
    return (T) this.defaults.get(type);
  }

}
