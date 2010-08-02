package joist.registry;

import joist.util.Log;

public class ResourceRefHolder<T> {

  public boolean loadOnStart = false;
  public Class<T> type;
  public ResourceRef<T> ref;
  public ResourceFactory<T> factory;

  public void startIfNeeded() {
    if (this.loadOnStart) {
      Log.debug("Loading on start: {}", this.type);
      this.ref.get();
    }
  }

  public void stopIfNeeded() {
    if (this.ref.isStarted()) {
      this.factory.destroy(this.ref.get());
    }
  }

}
