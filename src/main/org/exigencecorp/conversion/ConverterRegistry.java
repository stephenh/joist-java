package org.exigencecorp.conversion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.exigencecorp.util.Copy;
import org.exigencecorp.util.TestCounter;

public class ConverterRegistry {

    protected static final TestCounter probes = new TestCounter();
    private final ConcurrentMap<ConverterKey, ConverterStub> converters = new ConcurrentHashMap<ConverterKey, ConverterStub>();

    public static ConverterRegistry newRegistryWithDefaultConverters() {
        ConverterRegistry r = new ConverterRegistry();
        for (Converter<?, ?> c : DefaultConverters.all) {
            r.addConverter(c);
        }
        return r;
    }

    public synchronized <T, U> void addConverter(Converter<T, U> c) {
        ConverterKey key1 = new ConverterKey(c.getTypeOne(), c.getTypeTwo());
        ConverterStubOne<T, U> stub1 = new ConverterStubOne<T, U>(c);
        this.converters.put(key1, stub1);

        // This is kind of ugly--Converters were built to be inherently two-way--but they're not
        // always, so look for AbstractOneWayConverter as a marker interface. Maybe eventually go
        // back to one-way-only converters? Dunno.
        if (!(c instanceof AbstractOneWayConverter)) {
            ConverterKey key2 = new ConverterKey(c.getTypeTwo(), c.getTypeOne());
            ConverterStubTwo<T, U> stub2 = new ConverterStubTwo<T, U>(c);
            this.converters.put(key2, stub2);
        }
    }

    public <T> T convert(Object value, Class<T> toType) {
        Class<?> valueClass = (value == null) ? Void.class : value.getClass();
        ConverterKey key = new ConverterKey(valueClass, toType);
        ConverterStub stub = this.converters.get(key);
        if (stub != null) {
            return (T) stub.convert(value, toType);
        }

        // Do we even need to convert?
        ConverterRegistry.probes.next();
        if (toType.isAssignableFrom(valueClass)) {
            this.converters.put(key, new NoopConverterStub());
            return (T) value;
        }
        // We didn't find a converter with the default types, start walking up class/interface hierarchies
        for (Class<?> to : this.getSuperclassesAndInterfaces(toType)) {
            for (Class<?> from : this.getSuperclassesAndInterfaces(valueClass)) {
                ConverterKey probeKey = new ConverterKey(from, to);
                ConverterStub probeStub = this.converters.get(probeKey);
                if (probeStub != null) {
                    // This was a probed find, so cache this value
                    this.converters.put(key, probeStub);
                    return (T) probeStub.convert(value, toType);
                }
            }
        }

        throw new UnsupportedConversionException(value, toType);
    }

    private List<Class<?>> getSuperclassesAndInterfaces(Class<?> type) {
        List<Class<?>> possible = new ArrayList<Class<?>>();
        for (Class<?> current = type; current != null; current = current.getSuperclass()) {
            possible.add(current);
            possible.addAll(Copy.list(current.getInterfaces()));
        }
        return possible;
    }

    private static class ConverterKey {
        private final Class<?> from;
        private final Class<?> to;

        private ConverterKey(Class<?> from, Class<?> to) {
            this.from = from;
            this.to = to;
        }

        public boolean equals(Object other) {
            return this.from.equals(((ConverterKey) other).from) && this.to.equals(((ConverterKey) other).to);
        }

        public int hashCode() {
            return this.from.hashCode() + this.to.hashCode();
        }

        public String toString() {
            return this.from.getSimpleName() + " -> " + this.to.getSimpleName();
        }
    }

    private interface ConverterStub {
        abstract Object convert(Object value, Class<?> toType);
    }

    private static class ConverterStubOne<T, U> implements ConverterStub {
        private Converter<T, U> converter;

        public ConverterStubOne(Converter<T, U> converter) {
            this.converter = converter;
        }

        public Object convert(Object value, Class<?> toType) {
            return this.converter.convertOneToTwo((T) value, (Class<? extends U>) toType);
        }
    }

    private static class ConverterStubTwo<T, U> implements ConverterStub {
        private Converter<T, U> converter;

        public ConverterStubTwo(Converter<T, U> converter) {
            this.converter = converter;
        }

        public Object convert(Object value, Class<?> toType) {
            return this.converter.convertTwoToOne((U) value, (Class<? extends T>) toType);
        }
    }

    private static class NoopConverterStub implements ConverterStub {
        public Object convert(Object value, Class<?> toType) {
            return value;
        }
    }

}
