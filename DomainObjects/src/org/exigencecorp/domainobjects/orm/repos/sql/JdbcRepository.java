package org.exigencecorp.domainobjects.orm.repos.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Ids;
import org.exigencecorp.domainobjects.orm.repos.Repository;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Delete;
import org.exigencecorp.domainobjects.queries.Insert;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.queries.Update;
import org.exigencecorp.domainobjects.queries.Where;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.Copy;

public class JdbcRepository implements Repository {

    public static DataSource THIS_IS_DUMB = null;
    private Connection connection;

    public <T extends DomainObject> void delete(T instance) {
        Alias<? super T> current = (Alias<T>) instance.newAlias("t");
        while (current != null) {
            Delete<? super T> delete = Delete.from(current);
            delete.where(current.getIdColumn().equalsRuntimeChecked(instance.getId()));
            this.delete(delete);
            current = current.getBaseClassAlias();
        }
    }

    public <T extends DomainObject> void store(T instance) {
        if (instance.isNew()) {
            List<Insert<? super T>> inserts = new ArrayList<Insert<? super T>>();

            Alias<? super T> current = (Alias<T>) instance.newAlias("t");
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
        } else {
            Integer oldVersion = instance.getVersion();

            Alias<? super T> current = (Alias<T>) instance.newAlias("t");
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

    public <T extends DomainObject> void assignId(T instance) {
        Alias<T> t = (Alias<T>) instance.newAlias("t");

        String sql = "select nextval('" + t.getRootClassAlias().getTableName() + "_id_seq')";
        int id = Jdbc.queryForInt(this.connection, sql);
        t.getIdColumn().setJdbcValue(instance, id);
        t.getVersionColumn().setJdbcValue(instance, 0);

        // ((IdAliasColumn<T>) t.getIdColumn()).set(instance, new Id<T>((Class<T>) instance.getClass(), new Integer(id)));
        // t.getVersionColumn().set(instance, new Integer(0));

        ((AbstractDomainObject) instance).getChangedProperties().add("id"); // Hack
    }

    public <T extends DomainObject> void insert(Insert<T> insert) {
        new Inserter<T>(this.connection, insert).insert();
    }

    public <T extends DomainObject> void update(Update<T> update) {
        new Updater<T>(this.connection, update).update();
    }

    public <T extends DomainObject> void delete(Delete<T> delete) {
        new Deleter<T>(this.connection, delete).delete();
    }

    public <T extends DomainObject, R> List<R> select(Select<T> select, Class<R> instanceType) {
        return new Selecter<T>(this.connection, select).select(instanceType);
    }

    public <T extends DomainObject> Ids<T> selectIds(Select<T> select) {
        return new Selecter<T>(this.connection, select).selectIds();
    }

}
