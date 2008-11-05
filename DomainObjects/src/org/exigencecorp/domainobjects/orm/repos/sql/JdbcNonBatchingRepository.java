package org.exigencecorp.domainobjects.orm.repos.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Ids;
import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.orm.repos.Repository;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Delete;
import org.exigencecorp.domainobjects.queries.Insert;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.queries.Update;
import org.exigencecorp.domainobjects.queries.Where;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.Copy;

public class JdbcNonBatchingRepository implements Repository {

    private Connection connection;

    public <T extends DomainObject> T load(Class<T> type, Integer id) {
        T instance = (T) UoW.getCurrent().getIdentityMap().findOrNull(type, id);
        if (instance == null) {
            Alias<T> a = AliasRegistry.get(type);
            instance = Select.from(a).where(a.getIdColumn().equals(id)).unique();
        }
        return instance;
    }

    public <T extends DomainObject> void delete(T instance) {
        Alias<? super T> current = AliasRegistry.get(instance);
        while (current != null) {
            Delete<? super T> delete = Delete.from(current);
            delete.where(current.getIdColumn().equalsRuntimeChecked(instance.getId()));
            this.delete(delete);
            current = current.getBaseClassAlias();
        }
    }

    public <T extends DomainObject> void store(Set<T> instances) {
        for (T instance : instances) {
            if (instance.isNew()) {
                this.assignId(instance);
                this.storeNewObject(instance);
            } else {
                this.storeExistingObject(instance);
            }
        }
    }

    public void open() {
        try {
            this.connection = JdbcRepository.THIS_IS_DUMB.getConnection();
            this.connection.setAutoCommit(false);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    public void close() {
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
        try {
            this.connection.rollback();
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    public <T extends DomainObject> void insert(Insert<T> insert) {
        Jdbc.updateAll(this.connection, insert.toSql(), insert.getAllParameters());
    }

    public <T extends DomainObject> void update(Update<T> update) {
        Jdbc.updateAll(this.connection, update.toSql(), update.getAllParameters());
    }

    public <T extends DomainObject> void delete(Delete<T> delete) {
        Jdbc.updateAll(this.connection, delete.toSql(), delete.getAllParameters());
    }

    public <T extends DomainObject, R> List<R> select(Select<T> select, Class<R> instanceType) {
        return new Selecter<T>(this.connection, select).select(instanceType);
    }

    public <T extends DomainObject> Ids<T> selectIds(Select<T> select) {
        return new Selecter<T>(this.connection, select).selectIds();
    }

    private <T extends DomainObject> void assignId(T instance) {
        Alias<T> t = AliasRegistry.get(instance);

        String sql = "select nextval('" + t.getRootClassAlias().getTableName() + "_id_seq')";
        int id = Jdbc.queryForInt(this.connection, sql);
        t.getIdColumn().setJdbcValue(instance, id);
        t.getVersionColumn().setJdbcValue(instance, 0);

        ((AbstractDomainObject) instance).getChangedProperties().add("id"); // Hack so isNew() still returns true
    }

    private <T extends DomainObject> void storeNewObject(T instance) {
        List<Insert<? super T>> inserts = new ArrayList<Insert<? super T>>();
        Alias<? super T> current = AliasRegistry.get(instance);
        while (current != null) {
            Insert<? super T> q = Insert.into((Alias<T>) current);
            for (AliasColumn<? super T, ?, ?> c : current.getColumns()) {
                q.set(c.toSetItem(instance));
            }
            if (current.getBaseClassAlias() != null) {
                q.set(current.getIdColumn().toSetItem(instance));
            }
            inserts.add(q);
            current = current.getBaseClassAlias();
        }
        for (Insert<? super T> q : Copy.reverse(inserts)) {
            this.insert(q);
        }
    }

    private <T extends DomainObject> void storeExistingObject(T instance) {
        Integer oldVersion = instance.getVersion();
        Alias<? super T> current = AliasRegistry.get(instance);
        while (current != null) {
            Update<? super T> q = Update.into((Alias<T>) current);
            for (AliasColumn<? super T, ?, ?> c : current.getColumns()) {
                if (!c.skipUpdate()) {
                    q.set(c.toSetItem(instance));
                }
            }
            if (current.getBaseClassAlias() == null) {
                q.set(current.getVersionColumn().toSetItem(oldVersion + 1));
                q.where(Where.and(current.getIdColumn().equalsRuntimeChecked(instance.getId()), current.getVersionColumn().equals(oldVersion)));
            } else {
                q.where(current.getSubClassIdColumn().equalsRuntimeChecked(instance.getId()));
            }
            this.update(q);
            if (current.getBaseClassAlias() == null) {
                current.getVersionColumn().setJdbcValue(instance, oldVersion + 1);
            }
            current = current.getBaseClassAlias();
        }
    }

}
