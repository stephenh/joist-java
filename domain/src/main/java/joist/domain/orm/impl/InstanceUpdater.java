package joist.domain.orm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joist.domain.DomainObject;
import joist.domain.exceptions.OpLockException;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;
import joist.util.StringBuilderr;
import joist.util.Wrap;

/** A class that caches the SQL for how to update a given table, needs to be thread-safe. */
public class InstanceUpdater<T extends DomainObject> {

  private static final Map<Class<?>, InstanceUpdater<?>> updaters = new HashMap<Class<?>, InstanceUpdater<?>>();
  private final List<Step<T>> steps = new ArrayList<Step<T>>();

  public static <T extends DomainObject> InstanceUpdater<T> get(Class<T> clazz) {
    InstanceUpdater<T> updater = (InstanceUpdater<T>) InstanceUpdater.updaters.get(clazz);
    if (updater == null) {
      updater = new InstanceUpdater<T>(AliasRegistry.get(clazz));
      InstanceUpdater.updaters.put(clazz, updater);
    }
    return updater;
  }

  public InstanceUpdater(Alias<T> alias) {
    for (Alias<? super T> current = alias; current != null; current = current.getBaseClassAlias()) {
      this.steps.add(new Step<T>(current));
    }
  }

  public void update(List<T> instances) {
    for (Step<T> step : this.steps) {
      List<List<Object>> allParameters = new ArrayList<List<Object>>();
      for (T instance : instances) {
        step.tickVersionIfRootClass(instance);
        step.addParametersForInstance(allParameters, instance);
      }
      List<Integer> changed = Jdbc.updateBatch(UoW.getConnection(), step.sql, allParameters);
      this.assertUpdatesAreAllOne(instances, changed);
    }
  }

  private void assertUpdatesAreAllOne(List<T> instances, List<Integer> changed) {
    for (int i = 0; i < changed.size(); i++) {
      if (changed.get(i).intValue() != 1) {
        throw new OpLockException(instances.get(i));
      }
    }
  }

  /** A step for domain object T--might actually be a superclass table, so alias of ? super T. */
  private static class Step<T extends DomainObject> {
    private final Alias<? super T> alias;
    private final List<AliasColumn<? super T, ?, ?>> columns = new ArrayList<AliasColumn<? super T, ?, ?>>();
    private final String sql;

    private Step(Alias<? super T> alias) {
      this.alias = alias;
      for (AliasColumn<? super T, ?, ?> c : alias.getColumns()) {
        if (!"id".equals(c.getName())) {
          this.columns.add(c);
        }
      }
      this.sql = this.toSql(alias);
    }

    private void tickVersionIfRootClass(T instance) {
      if (this.alias.isRootClass()) {
        Long nextVersion = instance.getVersion() == null ? 1 : instance.getVersion() + 1;
        this.alias.getVersionColumn().setJdbcValue(instance, nextVersion);
      }
    }

    private void addParametersForInstance(List<List<Object>> allParameters, T instance) {
      List<Object> parameters = new ArrayList<Object>();
      for (AliasColumn<? super T, ?, ?> c : this.columns) {
        parameters.add(c.getJdbcValue(instance));
      }
      if (this.alias.isRootClass()) {
        parameters.add(this.alias.getIdColumn().getJdbcValue(instance));
        parameters.add(instance.getVersion() - 1); // assume the id has already been ticked
      } else {
        parameters.add(this.alias.getIdColumn().getJdbcValue(instance));
      }
      allParameters.add(parameters);
    }

    private String toSql(Alias<?> alias) {
      StringBuilderr s = new StringBuilderr();
      s.line("UPDATE {}", Wrap.quotes(alias.getTableName()));
      s.append(" SET ");
      for (AliasColumn<?, ?, ?> c : this.columns) {
        s.append(Wrap.quotes(c.getName()));
        s.append(" = ?, ");
      }
      s.stripLastCommaSpace();
      s.line();
      Where where;
      if (alias.isRootClass()) {
        // We throw away the Where.parameters for our allParameters, so just use dummy values
        where = alias.getIdColumn().eq(0).and(alias.getVersionColumn().eq(0l));
      } else {
        where = alias.getSubClassIdColumn().eq(0);
      }
      s.line(" WHERE {}", where.getSqlWithoutAliasPrefix(alias.getName()));
      s.stripTrailingNewLine();
      return s.toString();
    }
  }

}
