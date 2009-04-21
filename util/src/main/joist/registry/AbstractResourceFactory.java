package joist.registry;

import java.lang.reflect.ParameterizedType;

import joist.util.Reflection;

/** Provides default create/destroy implementations of {@link ResourceFactory}.
 *
 * See {@link AbstractResourceFactory#create()} and {@link AbstractResourceFactory#destroy(Object)}.
 */
public abstract class AbstractResourceFactory<T> implements ResourceFactory<T> {

    private final Class<T> clazz;

    protected AbstractResourceFactory() {
        for (Class<?> type = this.getClass(); type != null; type = type.getSuperclass()) {
            if (type.getSuperclass().equals(AbstractResourceFactory.class)) {
                ParameterizedType ptype = (ParameterizedType) type.getGenericSuperclass();
                this.clazz = (Class<T>) ptype.getActualTypeArguments()[0];
                return;
            }
        }
        throw new RuntimeException("Could not infer the type T from the AbstractResourceFactory subclass");
    }

    /** Creates a new instance with reflection based on this instance's parameterized type <code>T</code>. */
    public T create() {
        return Reflection.newInstance(this.clazz);
    }

    /** Empty destroy method, does nothing. */
    public void destroy(T resource) {
    }

}
