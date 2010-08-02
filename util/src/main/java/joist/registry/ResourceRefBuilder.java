package joist.registry;

public class ResourceRefBuilder<T> {

  private final ResourceRefs refs;
  private final Class<T> type;
  private final ResourceRefHolder<T> holder;

  public ResourceRefBuilder(ResourceRefs refs, Class<T> type) {
    this.refs = refs;
    this.type = type;
    this.holder = new ResourceRefHolder<T>();
    this.holder.type = type;
    this.holder.factory = new ReflectionResourceFactory<T>(this.type);
  }

  public ResourceRefBuilder<T> loadOnStart(boolean loadOnStart) {
    this.holder.loadOnStart = loadOnStart;
    return this;
  }

  public ResourceRefBuilder<T> factory(ResourceFactory<T> factory) {
    this.holder.factory = factory;
    return this;
  }

  public <U extends T> ResourceRefBuilder<T> impl(Class<U> impl) {
    this.holder.factory = new ReflectionResourceFactory<T>(impl);
    return this;
  }

  public ResourceRef<T> make() {
    ResourceRef<T> ref = new SingletonResourceRef<T>(this.holder.factory);
    this.holder.ref = ref;
    this.refs.register(this.holder);
    return ref;
  }

}
