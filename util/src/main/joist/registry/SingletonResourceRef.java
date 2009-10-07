package joist.registry;

public class SingletonResourceRef<T> implements ResourceRef<T> {

    // double check locking works with "volatile" in JDK5+
    private volatile T resource;
    private final ResourceFactory<T> factory;

    public SingletonResourceRef(ResourceFactory<T> factory) {
        this.factory = factory;
    }

    public boolean isStarted() {
        return this.resource != null;
    }

    public T get() {
        if (this.resource == null) {
            synchronized (this) {
                if (this.resource == null) {
                    this.resource = this.factory.create();
                }
            }
        }
        return this.resource;
    }

}
