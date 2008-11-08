package org.exigencecorp.domainobjects.orm.repos;

import java.sql.Connection;
import java.util.Set;

import org.exigencecorp.domainobjects.DomainObject;

public interface Repository {

    void open();

    void commit();

    void rollback();

    void close();

    Connection getConnection();

    /* Instance-level methods. */

    <T extends DomainObject> T load(Class<T> type, Integer id);

    <T extends DomainObject> void store(Set<T> instances);

    <T extends DomainObject> void delete(T instance);

}
