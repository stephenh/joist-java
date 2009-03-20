package joist.registry;

import java.util.LinkedHashMap;
import java.util.Map;

import joist.util.Reflection;

/** A bag for holding resources as pseudo-singletons that can later be destroyed. */
public class ResourceBag {

    private Map<Class<?>, Object> instancesByFactoryClass = new LinkedHashMap<Class<?>, Object>();

    /** @return an instance created by <code>new FactoryClass().create()</code>, 1 per <code>factoryClass</code> class (singleton) */
    public synchronized <T, U extends ResourceFactory<T>> T get(Class<U> factoryClass) {
        T instance = (T) this.instancesByFactoryClass.get(factoryClass);
        if (instance == null) {
            U factory = Reflection.newInstance(factoryClass);
            instance = factory.create();
            this.instancesByFactoryClass.put(factoryClass, instance);
        }
        return instance;
    }

    public synchronized <T, U extends ResourceFactory<T>> LazyResource<T> getLazy(final Class<U> factoryClass) {
        return new LazyResource<T>() {
            public T get() {
                return ResourceBag.this.get(factoryClass);
            }
        };
    }

    /** Calls <code>destroy</code> for all the resources created by factories. */
    public synchronized void destroyResources() {
        for (Map.Entry<Class<?>, Object> entry : this.instancesByFactoryClass.entrySet()) {
            ResourceFactory<Object> factory = (ResourceFactory<Object>) Reflection.newInstance(entry.getKey());
            factory.destroy(entry.getValue());
        }
        this.instancesByFactoryClass.clear();
    }

}
