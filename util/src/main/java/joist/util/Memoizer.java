package joist.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

// http://www.terracotta.org/web/display/orgsite/Recipe?recipe=memoizer
public class Memoizer<A, V> {

    private final ConcurrentMap<A, Future<V>> cache;
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.cache = new ConcurrentHashMap<A, Future<V>>();
        this.c = c;
    }

    public V compute(final A arg) {
        while (true) {
            Future<V> f = this.cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<V>() {
                    public V call() throws InterruptedException {
                        return Memoizer.this.c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval);
                f = this.cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                this.cache.remove(arg, f);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Set<Entry<A, V>> entrySet() {
        Map<A, V> s = new HashMap<A, V>();
        try {
            for (Entry<A, Future<V>> e : this.cache.entrySet()) {
                s.put(e.getKey(), e.getValue().get());
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return s.entrySet();
    }

    public void clear() {
        this.cache.clear();
    }

    public interface Computable<A, V> {
        V compute(A arg) throws InterruptedException;
    }
}
