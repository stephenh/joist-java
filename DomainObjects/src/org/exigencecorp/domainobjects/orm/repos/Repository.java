package org.exigencecorp.domainobjects.orm.repos;

import java.util.List;
import java.util.Set;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Ids;
import org.exigencecorp.domainobjects.queries.Insert;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.queries.Update;

public interface Repository {

    void open();

    void commit();

    void rollback();

    void close();

    /* Instance-level methods. */

    <T extends DomainObject> T load(Class<T> type, Integer id);

    <T extends DomainObject> void store(Set<T> instances);

    <T extends DomainObject> void delete(T instance);

    /* Table-level methods. */

    <T extends DomainObject> void insert(Insert<T> insert);

    <T extends DomainObject> void update(Update<T> update);

    <T extends DomainObject, R> List<R> select(Select<T> select, Class<R> rowInstanceType);

    <T extends DomainObject> Ids<T> selectIds(Select<T> select);

}
