package joist.registry;

import java.util.Map;

import joist.util.Memoizer;
import joist.util.Reflection;
import joist.util.Memoizer.Computable;

/** A bag for holding resources as pseudo-singletons that can later be destroyed. */
public class ResourceBag {

    private final Computable<Class<?>, Object> computer = new Computable<Class<?>, Object>() {
        public Object compute(Class<?> arg) throws InterruptedException {
            return ((ResourceFactory<?>) Reflection.newInstance(arg)).create();
        }
    };
    private final Memoizer<Class<?>, Object> instancesByFactoryClass = new Memoizer<Class<?>, Object>(this.computer);

    /** @return an instance created by <code>new FactoryClass().create()</code>, 1 per <code>factoryClass</code> class (singleton) */
    public <T, U extends ResourceFactory<T>> T get(final Class<U> factoryClass) {
        return (T) this.instancesByFactoryClass.compute(factoryClass);
    }

    public <T, U extends ResourceFactory<T>> LazyResource<T> getLazy(final Class<U> factoryClass) {
        return new LazyResource<T>() {
            public T get() {
                return ResourceBag.this.get(factoryClass);
            }
        };
    }

    /** Calls <code>destroy</code> for all the resources created by factories. */
    public void destroyResources() {
        for (Map.Entry<Class<?>, Object> entry : this.instancesByFactoryClass.entrySet()) {
            ResourceFactory<Object> factory = (ResourceFactory<Object>) Reflection.newInstance(entry.getKey());
            factory.destroy(entry.getValue());
        }
        this.instancesByFactoryClass.clear();
    }

}
