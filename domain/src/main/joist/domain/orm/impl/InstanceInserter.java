package joist.domain.orm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joist.domain.DomainObject;
import joist.domain.orm.AliasRegistry;
import joist.domain.queries.Alias;
import joist.domain.queries.columns.AliasColumn;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;
import joist.util.StringBuilderr;
import joist.util.Wrap;

import org.apache.commons.lang.StringUtils;

/** A class that caches SQL for how to insert a given table, needs to be thread-safe. */
public class InstanceInserter<T extends DomainObject> {

    private static final Map<Class<?>, InstanceInserter<?>> inserters = new HashMap<Class<?>, InstanceInserter<?>>();
    private final List<Step<T>> steps = new ArrayList<Step<T>>();

    public static <T extends DomainObject> InstanceInserter<T> get(Class<T> clazz) {
        InstanceInserter<T> inserter = (InstanceInserter<T>) InstanceInserter.inserters.get(clazz);
        if (inserter == null) {
            inserter = new InstanceInserter<T>(AliasRegistry.get(clazz));
            InstanceInserter.inserters.put(clazz, inserter);
        }
        return inserter;
    }

    public InstanceInserter(Alias<T> alias) {
        Alias<? super T> current = alias;
        while (current != null) {
            this.steps.add(new Step<T>(current));
            current = current.getBaseClassAlias();
        }
    }

    public void insert(List<T> instances) {
        for (Step<T> step : this.steps) {
            List<List<Object>> allParameters = new ArrayList<List<Object>>();
            for (T instance : instances) {
                step.addParametersForInstance(allParameters, instance);
            }
            Jdbc.updateBatch(UoW.getConnection(), step.sql, allParameters);
        }
    }

    /** One step-per-inheritance table. */
    private static class Step<T extends DomainObject> {
        private final List<AliasColumn<? super T, ?, ?>> columns = new ArrayList<AliasColumn<? super T, ?, ?>>();
        private final String sql;

        private Step(Alias<? super T> alias) {
            for (AliasColumn<? super T, ?, ?> column : alias.getColumns()) {
                this.columns.add(column);
            }
            if (!alias.isRootClass()) {
                this.columns.add(alias.getIdColumn());
            }
            this.sql = this.toSql(alias);
        }

        private void addParametersForInstance(List<List<Object>> allParameters, T instance) {
            List<Object> parameters = new ArrayList<Object>();
            for (AliasColumn<? super T, ?, ?> column : this.columns) {
                parameters.add(column.getJdbcValue(instance));
            }
            allParameters.add(parameters);
        }

        private String toSql(Alias<?> alias) {
            StringBuilderr s = new StringBuilderr();
            s.append("INSERT INTO ");
            s.append(Wrap.quotes(alias.getTableName()));
            s.append(" (");
            for (AliasColumn<?, ?, ?> column : this.columns) {
                s.append(column.getName());
                s.append(", ");
            }
            s.stripLastCommaSpace();
            s.append(")");
            s.append(" VALUES (");
            s.append(StringUtils.repeat("?, ", this.columns.size()));
            s.stripLastCommaSpace();
            s.append(")");
            return s.toString();
        }
    }

}
