package joist.domain.orm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.DataSource;

import joist.domain.DomainObject;
import joist.domain.queries.Alias;
import joist.domain.queries.Delete;
import joist.domain.queries.Insert;
import joist.domain.queries.Select;
import joist.domain.queries.Update;
import joist.domain.queries.columns.AliasColumn;
import joist.domain.uow.UoW;


import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.jdbc.RowMapper;
import org.exigencecorp.util.Join;
import org.exigencecorp.util.MapToList;

public class Repository {

    public static DataSource datasource = null;
    private Connection connection;

    public <T extends DomainObject> T load(Class<T> type, Integer id) {
        T instance = (T) UoW.getIdentityMap().findOrNull(type, id);
        if (instance == null) {
            Alias<T> a = AliasRegistry.get(type);
            instance = Select.from(a).where(a.getIdColumn().equals(id)).unique();
        }
        return instance;
    }

    public <T extends DomainObject> void delete(T instance) {
        Alias<? super T> current = AliasRegistry.get(instance);
        while (current != null) {
            Delete.from(current).where(current.getIdColumn().equals(instance)).execute();
            current = current.getBaseClassAlias();
        }
    }

    public <T extends DomainObject> void store(Set<T> instances) {
        MapToList<Class<T>, T> byClassInserts = new MapToList<Class<T>, T>();
        MapToList<Class<T>, T> byClassUpdates = new MapToList<Class<T>, T>();
        for (T instance : instances) {
            if (instance.isNew()) {
                byClassInserts.add(instance.getClass(), instance);
            } else {
                byClassUpdates.add(instance.getClass(), instance);
            }
        }
        this.assignIds(byClassInserts);
        for (Entry<Class<T>, List<T>> entry : byClassInserts.entrySet()) {
            this.batchInserts(entry.getKey(), entry.getValue());
        }
        for (Entry<Class<T>, List<T>> entry : byClassUpdates.entrySet()) {
            this.batchUpdates(entry.getKey(), entry.getValue());
        }
    }

    public void open() {
        try {
            if (Repository.datasource == null) {
                throw new RuntimeException("The repository database has not been configured.");
            }
            this.connection = Repository.datasource.getConnection();
            this.connection.setAutoCommit(false);
            Jdbc.update(this.connection, "SET CONSTRAINTS ALL DEFERRED;");
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

    private <T extends DomainObject> void batchInserts(Class<T> domainClass, List<T> instances) {
        Alias<? super T> current = AliasRegistry.get(domainClass);
        while (current != null) {
            Insert<? super T> q = Insert.intoTemplate(current);
            for (AliasColumn<? super T, ?, ?> column : current.getColumns()) {
                q.addColumnName(column.getName());
            }
            if (!current.isRootClass()) {
                q.addColumnName(current.getIdColumn().getName());
            }
            for (T instance : instances) {
                List<Object> parameters = new ArrayList<Object>();
                for (AliasColumn<? super T, ?, ?> column : current.getColumns()) {
                    parameters.add(column.getJdbcValue(instance));
                }
                if (!current.isRootClass()) {
                    parameters.add(current.getIdColumn().getJdbcValue(instance));
                }
                q.addMoreParameters(parameters);
            }
            q.execute();
            current = current.getBaseClassAlias();
        }
    }

    private <T extends DomainObject> void batchUpdates(Class<T> domainClass, List<T> instances) {
        Alias<? super T> current = AliasRegistry.get(domainClass);
        while (current != null) {
            Update<? super T> q = Update.intoTemplate(current);
            for (AliasColumn<? super T, ?, ?> c : current.getColumns()) {
                if (!c.skipUpdate()) {
                    q.addColumnName(c.getName());
                }
            }
            if (current.isRootClass()) {
                q.addColumnName(current.getVersionColumn().getName());
                q.where(current.getIdColumn().equals(0).and(current.getVersionColumn().equals(0)));
            } else {
                q.where(current.getSubClassIdColumn().equals(0));
            }
            for (T instance : instances) {
                List<Object> parameters = new ArrayList<Object>();
                for (AliasColumn<? super T, ?, ?> c : current.getColumns()) {
                    if (!c.skipUpdate()) {
                        parameters.add(c.getJdbcValue(instance));
                    }
                }
                if (current.isRootClass()) {
                    parameters.add(instance.getVersion() + 1);
                    parameters.add(current.getIdColumn().getJdbcValue(instance));
                    parameters.add(current.getVersionColumn().getJdbcValue(instance));
                    current.getVersionColumn().setJdbcValue(instance, instance.getVersion() + 1);
                } else {
                    parameters.add(current.getIdColumn().getJdbcValue(instance));
                }
                q.addMoreParameters(parameters);
            }
            List<Integer> changed = q.execute();
            for (int i = 0; i < changed.size(); i++) {
                if (changed.get(i).intValue() != 1) {
                    throw new RuntimeException("Op lock failed for " + instances.get(i));
                }
            }
            current = current.getBaseClassAlias();
        }
    }

    private <T extends DomainObject> void assignIds(MapToList<Class<T>, T> byClassInserts) {
        List<String> allSql = new ArrayList<String>();
        for (Entry<Class<T>, List<T>> entry : byClassInserts.entrySet()) {
            String sql = "select nextval('" + AliasRegistry.get(entry.getKey()).getRootClassAlias().getTableName() + "_id_seq')";
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (entry.getValue().get(i).getId() != null) {
                    AliasRegistry.get(entry.getKey()).getVersionColumn().setJdbcValue(entry.getValue().get(i), 0);
                    continue; // skip new objects that got manually assigned an id
                }
                allSql.add(sql);
            }
        }

        if (allSql.size() == 0) {
            return;
        }

        final List<Integer> ids = new ArrayList<Integer>();
        Jdbc.query(this.connection, Join.join(allSql, " UNION ALL "), new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                ids.add(rs.getInt(1));
            }
        });

        int i = 0;
        for (Entry<Class<T>, List<T>> entry : byClassInserts.entrySet()) {
            Alias<T> t = AliasRegistry.get(entry.getKey());
            for (T instance : entry.getValue()) {
                int id = ids.get(i++);
                instance.setId(id);
                t.getVersionColumn().setJdbcValue(instance, 0);
            }
        }
    }
}
