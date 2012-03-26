package joist.domain.orm.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import joist.domain.DomainObject;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;
import joist.util.StringBuilderr;
import joist.util.Wrap;

import org.apache.commons.lang.StringUtils;

/** A class that caches SQL for how to insert a given table, needs to be thread-safe. */
public class InstanceInserter<T extends DomainObject> {

  private static final Map<Class<?>, InstanceInserter<?>> inserters = new ConcurrentHashMap<Class<?>, InstanceInserter<?>>();
  private final List<Step<T>> stepsNewId = new ArrayList<Step<T>>();
  private final List<Step<T>> stepsHasId = new ArrayList<Step<T>>();
  private final Alias<? super T> alias;

  public static <T extends DomainObject> InstanceInserter<T> get(Class<T> clazz) {
    InstanceInserter<T> inserter = (InstanceInserter<T>) InstanceInserter.inserters.get(clazz);
    if (inserter == null) {
      inserter = new InstanceInserter<T>(AliasRegistry.get(clazz));
      InstanceInserter.inserters.put(clazz, inserter);
    }
    return inserter;
  }

  public InstanceInserter(Alias<T> alias) {
    this.alias = alias;
    for (Alias<? super T> current = alias; current != null; current = current.getBaseClassAlias()) {
      this.stepsHasId.add(new Step<T>(current, true));
      this.stepsNewId.add(new Step<T>(current, false));
    }
    Collections.reverse(this.stepsHasId); // do base classes first
    Collections.reverse(this.stepsNewId); // do base classes first
  }

  /** Uses a batch update to insert instances that already have ids from sequences. */
  public void insertHasId(List<T> instances) {
    if (instances.size() == 0) {
      return;
    }
    for (Step<T> step : this.stepsHasId) {
      List<List<Object>> allParameters = new ArrayList<List<Object>>();
      for (T instance : instances) {
        this.alias.getVersionColumn().setJdbcValue(instance, 0l);
        step.addParametersForInstance(allParameters, instance);
      }
      Jdbc.updateBatch(UoW.getConnection(), step.sql, allParameters);
    }
  }

  /** Uses a batch insert to get back the auto-assigned ids for {@code instances}. */
  public void insertNewId(List<T> instances) {
    if (instances.size() == 0) {
      return;
    }
    for (Step<T> step : this.stepsNewId) {
      List<List<Object>> allParameters = new ArrayList<List<Object>>();
      for (T instance : instances) {
        this.alias.getVersionColumn().setJdbcValue(instance, 0l);
        step.addParametersForInstance(allParameters, instance);
      }
      Long[] keys = Jdbc.insertBatch(UoW.getConnection(), step.sql, allParameters);
      if (step == this.stepsNewId.get(0)) {
        for (int i = 0; i < keys.length; i++) {
          T instance = instances.get(i);
          this.alias.getIdColumn().setJdbcValue(instance, keys[i]);
          UoW.getIdentityMap().store(instance);
        }
      }
    }
  }

  /** One step-per-inheritance table. */
  private static class Step<T extends DomainObject> {
    private final Alias<? super T> alias;
    private final List<AliasColumn<? super T, ?, ?>> columns = new ArrayList<AliasColumn<? super T, ?, ?>>();
    private final String sql;
    private final boolean hasId;

    private Step(Alias<? super T> alias, boolean hasId) {
      this.alias = alias;
      for (AliasColumn<? super T, ?, ?> column : alias.getColumns()) {
        this.columns.add(column);
      }
      if (!alias.isRootClass()) {
        this.columns.add(alias.getIdColumn());
      }
      this.hasId = hasId;
      this.sql = this.toSql(alias);
    }

    private void addParametersForInstance(List<List<Object>> allParameters, T instance) {
      List<Object> parameters = new ArrayList<Object>();
      for (AliasColumn<? super T, ?, ?> column : this.columns) {
        if (column instanceof IdAliasColumn<?> && this.alias.isRootClass() && !this.hasId) {
          continue;
        }
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
        if (column instanceof IdAliasColumn<?> && this.alias.isRootClass() && !this.hasId) {
          continue;
        }
        s.append(Wrap.quotes(column.getName()));
        s.append(", ");
      }
      s.stripLastCommaSpace();
      s.append(")");
      s.append(" VALUES (");
      if (this.alias.isRootClass() && !this.hasId) {
        s.append(StringUtils.repeat("?, ", this.columns.size() - 1));
      } else {
        s.append(StringUtils.repeat("?, ", this.columns.size()));
      }
      s.stripLastCommaSpace();
      s.append(")");
      return s.toString();
    }
  }

}
