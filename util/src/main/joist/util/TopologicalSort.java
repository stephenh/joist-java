package joist.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TopologicalSort<T> {

    private final MapToList<T, T> dependencies = new MapToList<T, T>();
    private List<T> cached;

    public void addNode(T node) {
        this.dependencies.add(node);
    }

    public void addDependency(T dependent, T parent) {
        this.ensureNodes(dependent, parent);
        this.dependencies.add(dependent, parent);
        if (this.hasCycles()) {
            throw new CycleException(dependent, parent);
        }
    }

    public void addDependencyIfNoCycle(T dependent, T parent) {
        this.ensureNodes(dependent, parent);
        this.dependencies.add(dependent, parent);
        if (this.hasCycles()) {
            this.dependencies.remove(dependent, parent);
        }
    }

    public List<T> get() {
        if (this.cached == null) {
            this.cached = this.sort();
        }
        return this.cached;
    }

    private List<T> sort() {
        List<T> sorted = new ArrayList<T>();
        List<T> nodesLeft = Copy.list(this.dependencies.keySet());
        MapToList<T, T> dependenciesLeft = Copy.map(this.dependencies);
        while (nodesLeft.size() > 0) {
            T nextNode = this.findNodeWithNoDependencies(nodesLeft, dependenciesLeft);
            this.removeDependenciesForParent(dependenciesLeft, nextNode);
            this.removeNode(nodesLeft, nextNode);
            sorted.add(nextNode);
        }
        return sorted;
    }

    private T findNodeWithNoDependencies(List<T> nodesLeft, MapToList<T, T> dependenciesLeft) {
        for (T node : nodesLeft) {
            if (dependenciesLeft.get(node).size() == 0) {
                return node;
            }
        }
        throw new CycleException();
    }

    private void removeDependenciesForParent(MapToList<T, T> dependenciesLeft, T node) {
        for (Map.Entry<T, List<T>> e : dependenciesLeft.entrySet()) {
            while (e.getValue().contains(node)) {
                e.getValue().remove(node);
            }
        }
    }

    private boolean hasCycles() {
        try {
            this.cached = this.sort();
            return false;
        } catch (CycleException ce) {
            return true;
        }
    }

    private void removeNode(List<T> nodesLeft, T node) {
        nodesLeft.remove(node);
    }

    private void ensureNodes(T dependent, T parent) {
        if (!this.dependencies.containsKey(dependent)) {
            throw new RuntimeException("The dependent is not yet a node: " + dependent);
        }
        if (!this.dependencies.containsKey(parent)) {
            throw new RuntimeException("The parent is not yet a node: " + parent);
        }
    }

    public static class CycleException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public CycleException() {
            super("cycle");
        }

        public CycleException(Object dependent, Object parent) {
            super("cycle occurred adding " + dependent + " -> " + parent);
        }
    }

}
