package org.exigencecorp.registry;

import java.util.LinkedHashMap;
import java.util.Map;

import org.exigencecorp.util.Reflection;

/** A bag for holding resources as pseudo-singletons and can later be destroyed. */
public class ResourceBag {

    private Map<ResourceFactory<Object>, Object> factoryInstanceToResource = new LinkedHashMap<ResourceFactory<Object>, Object>();
    private Map<Class<?>, Object> factoryClassToResource = new LinkedHashMap<Class<?>, Object>();
    private Map<Class<?>, Object> instanceClassToResource = new LinkedHashMap<Class<?>, Object>();

    /** @return an instance created by <code>new FactoryClass().create()</code>, 1 per <code>factoryClass</code> */
    public synchronized <T, U extends ResourceFactory<T>> T getResourceForFactoryClass(Class<U> factoryClass) {
        T instance = (T) this.factoryClassToResource.get(factoryClass);
        if (instance == null) {
            U factory = Reflection.newInstance(factoryClass);
            instance = factory.create();
            this.factoryClassToResource.put(factoryClass, instance);
        }
        return instance;
    }

    /** @return an instance of <code>instanceClass</code>, 1 per <code>instanceClass</code> */
    public synchronized <T> T getResourceForInstanceClass(Class<T> instanceClass) {
        T instance = (T) this.instanceClassToResource.get(instanceClass);
        if (instance == null) {
            instance = Reflection.newInstance(instanceClass);
            this.instanceClassToResource.put(instanceClass, instance);
        }
        return instance;
    }

    /** @return an instance created by <code>factory.create()</code>, 1 per <code>factory</code> */
    public synchronized <T> T getResourceForFactoryInstance(ResourceFactory<T> factory) {
        T resource = (T) this.factoryInstanceToResource.get(factory);
        if (resource == null) {
            resource = factory.create();
            this.factoryInstanceToResource.put((ResourceFactory<Object>) factory, resource);
        }
        return resource;
    }

    /** Calls <code>destroy</code> on all the resources created by factories. */
    public synchronized void destroyResources() {
        for (Map.Entry<ResourceFactory<Object>, Object> entry : this.factoryInstanceToResource.entrySet()) {
            entry.getKey().destroy(entry.getValue());
        }
        for (Map.Entry<Class<?>, Object> entry : this.factoryClassToResource.entrySet()) {
            ResourceFactory<Object> factory = (ResourceFactory<Object>) Reflection.newInstance(entry.getKey());
            factory.destroy(entry.getValue());
        }
    }

}
