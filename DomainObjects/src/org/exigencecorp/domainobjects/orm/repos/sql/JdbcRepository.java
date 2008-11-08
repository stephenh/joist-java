package org.exigencecorp.domainobjects.orm.repos.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.DomainObject;
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
import org.exigencecorp.jdbc.RowMapper;
import org.exigencecorp.util.MapToList;

public class JdbcRepository implements Repository {

    public static DataSource THIS_IS_DUMB = null;
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
            Delete.from(current).where(current.getIdColumn().equalsRuntimeChecked(instance.getId())).execute();
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
            this.connection = JdbcRepository.THIS_IS_DUMB.getConnection();
            this.connection.setAutoCommit(false);
            Jdbc.executeUpdate(this.connection, "SET CONSTRAINTS ALL DEFERRED;");
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
                q.where(Where.and(current.getIdColumn().equals(0), current.getVersionColumn().equals(0)));
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
            q.execute();
            current = current.getBaseClassAlias();
        }
    }

    private <T extends DomainObject> void assignIds(MapToList<Class<T>, T> byClassInserts) {
        if (byClassInserts.size() == 0) {
            return;
        }

        List<String> allSql = new ArrayList<String>();
        for (Entry<Class<T>, List<T>> entry : byClassInserts.entrySet()) {
            String sql = "select nextval('" + AliasRegistry.get(entry.getKey()).getRootClassAlias().getTableName() + "_id_seq')";
            for (int i = 0; i < entry.getValue().size(); i++) {
                allSql.add(sql);
            }
        }

        final List<Integer> ids = new ArrayList<Integer>();
        Jdbc.query(this.connection, StringUtils.join(allSql, " UNION ALL "), new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                ids.add(rs.getInt(1));
            }
        });

        int i = 0;
        for (Entry<Class<T>, List<T>> entry : byClassInserts.entrySet()) {
            Alias<T> t = AliasRegistry.get(entry.getKey());
            for (T instance : entry.getValue()) {
                int id = ids.get(i++);
                t.getIdColumn().setJdbcValue(instance, id);
                t.getVersionColumn().setJdbcValue(instance, 0);
                ((AbstractDomainObject) instance).getChangedProperties().add("id"); // Hack so isNew() still returns true
            }
        }
    }
}
