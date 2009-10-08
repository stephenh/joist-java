package joist.domain.orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.DataSource;

import joist.domain.DomainObject;
import joist.domain.orm.impl.InstanceInserter;
import joist.domain.orm.impl.InstanceUpdater;
import joist.domain.orm.impl.SortedInstances;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Delete;
import joist.domain.orm.queries.Select;
import joist.domain.uow.UoW;
import joist.registry.ResourceRef;

public class Repository {

    public static ResourceRef<DataSource> datasource = null;
    private Connection connection;

    public <T extends DomainObject> T load(Class<T> type, Integer id) {
        T instance = (T) UoW.getIdentityMap().findOrNull(type, id);
        if (instance == null) {
            Alias<T> a = AliasRegistry.get(type);
            instance = Select.from(a).where(a.getIdColumn().equals(id)).unique();
        }
        return instance;
    }

    public void delete(DomainObject instance) {
        Alias<? super DomainObject> current = AliasRegistry.get(instance);
        while (current != null) {
            Delete.from(current).where(current.getIdColumn().equals(instance)).execute();
            current = current.getBaseClassAlias();
        }
    }

    public void store(Set<DomainObject> instances) {
        SortedInstances sorted = new SortedInstances(instances);
        // new IdAssigner().assignIds(sorted.inserts);
        for (Class<DomainObject> key : sorted.insertsByForeignKey) {
            InstanceInserter.get(key).insert(sorted.inserts.get(key));
        }
        for (Entry<Class<DomainObject>, List<DomainObject>> entry : sorted.updates.entrySet()) {
            InstanceUpdater.get(entry.getKey()).update(entry.getValue());
        }
    }

    public void open() {
        if (Repository.datasource == null) {
            throw new RuntimeException("The repository database has not been configured.");
        }
        try {
            this.connection = Repository.datasource.get().getConnection();
            this.connection.setAutoCommit(false);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    public void close() {
        if (this.connection == null) {
            return;
        }
        try {
            this.connection.close();
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    public void commit() {
        try {
            this.connection.commit();
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    public void rollback() {
        if (this.connection == null) {
            return;
        }
        try {
            this.connection.rollback();
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

}
