package joist.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TopologicalSort<T> {

    private final List<T> nodes = new ArrayList<T>();
    private final Map<T, T> dependencies = new HashMap<T, T>();

    public void addNode(T node) {
        this.nodes.add(node);
    }

    public void addDependency(T dependent, T parent) {
        if (!this.nodes.contains(dependent)) {
            throw new RuntimeException("The dependent is not yet a node: " + dependent);
        }
        if (!this.nodes.contains(parent)) {
            throw new RuntimeException("The parent is not yet a node: " + parent);
        }
        this.dependencies.put(dependent, parent);
    }

    public List<T> sort() {
        List<T> sorted = new ArrayList<T>();
        while (this.nodes.size() > 0) {
            T next = this.findNodeWithNoDependencies();
            this.removeDependenciesForParent(next);
            this.removeNode(next);
            sorted.add(next);
        }
        return sorted;
    }

    private T findNodeWithNoDependencies() {
        for (T node : this.nodes) {
            if (!this.dependencies.keySet().contains(node)) {
                return node;
            }
        }
        throw new RuntimeException("cycle");
    }

    private void removeDependenciesForParent(T node) {
        for (Iterator<Entry<T, T>> i = this.dependencies.entrySet().iterator(); i.hasNext();) {
            if (i.next().getValue().equals(node)) {
                i.remove();
            }
        }
    }

    private void removeNode(T node) {
        this.nodes.remove(node);
    }

}
