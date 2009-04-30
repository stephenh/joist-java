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
        this.ensureNodes(dependent, parent);
        this.dependencies.put(dependent, parent);
        if (this.hasCycles()) {
            throw new CycleException(dependent, parent);
        }
    }

    public void addWeakDependency(T dependent, T parent) {
        this.ensureNodes(dependent, parent);
        this.dependencies.put(dependent, parent);
        if (this.hasCycles()) {
            this.dependencies.remove(dependent);
        }
    }

    public List<T> sort() {
        List<T> sorted = new ArrayList<T>();
        List<T> nodesLeft = Copy.list(this.nodes);
        Map<T, T> dependenciesLeft = new HashMap<T, T>(this.dependencies);
        while (nodesLeft.size() > 0) {
            T next = this.findNodeWithNoDependencies(nodesLeft, dependenciesLeft);
            this.removeDependenciesForParent(dependenciesLeft, next);
            this.removeNode(nodesLeft, next);
            sorted.add(next);
        }
        return sorted;
    }

    private T findNodeWithNoDependencies(List<T> nodesLeft, Map<T, T> dependenciesLeft) {
        for (T node : nodesLeft) {
            if (!dependenciesLeft.keySet().contains(node)) {
                return node;
            }
        }
        throw new CycleException();
    }

    private void removeDependenciesForParent(Map<T, T> dependenciesLeft, T node) {
        for (Iterator<Entry<T, T>> i = dependenciesLeft.entrySet().iterator(); i.hasNext();) {
            if (i.next().getValue().equals(node)) {
                i.remove();
            }
        }
    }

    private boolean hasCycles() {
        try {
            this.sort();
            return false;
        } catch (CycleException ce) {
            return true;
        }
    }

    private void removeNode(List<T> nodesLeft, T node) {
        nodesLeft.remove(node);
    }

    private void ensureNodes(T dependent, T parent) {
        if (!this.nodes.contains(dependent)) {
            throw new RuntimeException("The dependent is not yet a node: " + dependent);
        }
        if (!this.nodes.contains(parent)) {
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
