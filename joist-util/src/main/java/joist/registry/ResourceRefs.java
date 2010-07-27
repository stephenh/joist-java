package joist.registry;

import java.util.ArrayList;
import java.util.List;

public class ResourceRefs {

    private final List<ResourceRefHolder<?>> refs = new ArrayList<ResourceRefHolder<?>>();

    public <T> ResourceRefBuilder<T> newRef(Class<T> type) {
        return new ResourceRefBuilder<T>(this, type);
    }

    public void start() {
        for (ResourceRefHolder<?> holder : this.refs) {
            holder.startIfNeeded();
        }
    }

    public void stop() {
        for (ResourceRefHolder<?> holder : this.refs) {
            holder.stopIfNeeded();
        }
    }

    public void register(ResourceRefHolder<?> holder) {
        this.refs.add(holder);
    }

}
