package joist.registry2;

import java.util.ArrayList;
import java.util.List;

public class Refs {

  private final List<Ref<?>> refs = new ArrayList<Ref<?>>();

  public <T> Ref<T> of(Class<T> type) {
    Ref<T> ref = new Ref<T>();
    this.refs.add(ref);
    return ref;
  }

  public void stop() {
    for (Ref<?> ref : this.refs) {
      ref.unset();
    }
  }

}
