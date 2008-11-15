package org.exigencecorp.domainobjects.orm.converters;

public interface Converter<U, V> {

    V toJdbc(U value);

    U toDomain(V value);

}
