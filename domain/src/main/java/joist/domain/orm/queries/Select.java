package joist.domain.orm.queries;

import java.util.ArrayList;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.exceptions.NotFoundException;
import joist.domain.exceptions.TooManyException;
import joist.domain.orm.mappers.DataTransferObjectMapper;
import joist.domain.orm.mappers.DomainObjectMapper;
import joist.domain.orm.mappers.IdsMapper;
import joist.domain.orm.mappers.ValuesMapper;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;
import joist.jdbc.RowMapper;
import joist.util.Copy;
import joist.util.Join;
import joist.util.StringBuilderr;
import joist.util.Wrap;

public class Select<T extends DomainObject> {

  public static <T extends DomainObject> Select<T> from(Alias<T> alias) {
    return new Select<T>(alias);
  }

  private final Alias<T> from;
  private final List<JoinClause<?, ?>> joins = new ArrayList<JoinClause<?, ?>>();
  private final List<SelectItem> selectItems = new ArrayList<SelectItem>();
  private Where where = null;
  private Where having = null;
  private Order[] orderBy = null;
  private AliasColumn<?, ?, ?>[] groupBy = null;
  private Integer limit;
  private Integer offset;

  private Select(Alias<T> alias) {
    this.from = alias;
    this.addInnerJoinsForBaseClasses();
    for (AliasColumn<T, ?, ?> c : alias.getColumns()) {
      this.selectItems.add(new SelectItem(c));
    }
    this.addOuterJoinsForSubClasses();
  }

  public Select<T> join(JoinClause<?, ?> join) {
    return this.join(join, true);
  }

  public Select<T> select(SelectItem... selectItems) {
    this.selectItems.clear();
    this.selectItems.addAll(Copy.list(selectItems));
    return this;
  }

  public Select<T> where(Where where) {
    this.where = where;
    return this;
  }

  public Select<T> having(Where where) {
    this.having = where;
    return this;
  }

  public Select<T> orderBy(Order... columns) {
    this.orderBy = columns;
    return this;
  }

  public Select<T> limit(Integer limit) {
    this.limit = limit;
    return this;
  }

  public Select<T> offset(Integer offset) {
    this.offset = offset;
    return this;
  }

  public List<T> list() {
    return this.list(this.from.getDomainClass());
  }

  public PagedList<T> paged() {
    return new PagedList<T>(this);
  }

  public T unique() {
    return this.unique(this.from.getDomainClass());
  }

  public T uniqueOrNull() {
    return this.uniqueOrNull(this.from.getDomainClass());
  }

  public <R> List<R> list(Class<R> rowType) {
    final List<R> results = new ArrayList<R>();
    RowMapper mapper = null;
    if (this.isLoadingDomainObjects(rowType)) {
      mapper = new DomainObjectMapper<T>(this.from, (List<T>) results);
    } else {
      mapper = new DataTransferObjectMapper<T, R>(this.selectItems, rowType, results);
    }
    Jdbc.query(UoW.getConnection(), this.toSql(), this.getParameters(), mapper);
    return results;
  }

  public <R> List<R> listValues(Class<R> valueType) {
    final List<R> results = new ArrayList<R>();
    if (this.selectItems.size() != 1) {
      throw new IllegalStateException("listValues expects to only query a single column");
    }
    Jdbc.query(//
      UoW.getConnection(),
      this.toSql(),
      this.getParameters(),
      new ValuesMapper<R>(this.selectItems.get(0), results));
    return results;
  }

  public <R> R unique(Class<R> rowType) {
    R result = this.uniqueOrNull(rowType);
    if (result == null) {
      throw new NotFoundException(rowType);
    }
    return result;
  }

  public <R> R uniqueOrNull(Class<R> rowType) {
    List<R> results = this.list(rowType);
    if (results.size() == 0) {
      return null;
    } else if (results.size() > 1) {
      throw new TooManyException(rowType, results);
    }
    return results.get(0);
  }

  public <R> R uniqueValueOrNull(Class<R> rowType) {
    List<R> results = this.listValues(rowType);
    if (results.size() == 0) {
      return null;
    } else if (results.size() > 1) {
      throw new TooManyException(rowType, results);
    }
    return results.get(0);
  }

  public List<Long> listIds() {
    List<Long> ids = new ArrayList<Long>();
    this.select(this.from.getIdColumn().as("id"));
    this.orderBy(this.from.getIdColumn().asc()); // determinism
    Jdbc.query(UoW.getConnection(), this.toSql(), this.getParameters(), new IdsMapper<T>(this.from, ids));
    return ids;
  }

  public long count() {
    // Make a copy countQuery so we can discard any select items and order bys and not mess up this query
    Select<T> countQuery = Select.from(this.from);
    countQuery.joins.addAll(this.joins.subList(countQuery.joins.size(), this.joins.size()));
    countQuery.where = this.where;
    countQuery.offset = this.offset;
    countQuery.limit = this.limit;
    countQuery.select(new SelectItem("count(distinct " + this.from.getIdColumn().getQualifiedName() + ")", "count"));
    return countQuery.unique(Count.class).count;
  }

  public static class Count {
    public Long count;
  }

  public Where getWhere() {
    return this.where;
  }

  public Order[] getOrderBy() {
    return this.orderBy;
  }

  public void groupBy(AliasColumn<?, ?, ?>... columns) {
    this.groupBy = columns;
  }

  public SelectCountBuilder count = new SelectCountBuilder();

  @Deprecated
  /**
   * Use joist.domain.orm.queries.columns.Aggregate.count() instead
   */
  public class SelectCountBuilder {
    public SelectItem as(String name) {
      return new SelectItem("count(*)", name);
    }

    public Where lessThan(int number) {
      return new Where("count(*) < ?", number);
    }

    public Where greatherThan(int number) {
      return new Where("count(*) > ?", number);
    }
  }

  public String toSql() {
    StringBuilderr s = new StringBuilderr();
    // since Select is only ever used to return entities (or ids/DTOS
    // of entities), we don't want a join to return multiple rows of
    // the parent entity for each child entity in the join.
    if (this.joins.size() > 0) {
      s.line("SELECT DISTINCT {}", Join.commaSpace(this.selectItems));
    } else {
      s.line("SELECT {}", Join.commaSpace(this.selectItems));
    }
    s.line(" FROM {} {}", Wrap.quotes(this.from.getTableName()), this.from.getName());
    for (JoinClause<?, ?> join : this.joins) {
      s.line(" {}", join);
    }
    if (this.getWhere() != null) {
      s.line(" WHERE {}", this.getWhere());
    }
    if (this.groupBy != null) {
      s.line(" GROUP BY {}", Join.comma(this.groupBy));
    }
    if (this.having != null) {
      s.line(" HAVING {}", this.having);
    }
    if (this.getOrderBy() != null) {
      s.line(" ORDER BY {}", Join.commaSpace(this.getOrderBy()));
    }
    if (this.limit != null) {
      s.line(" LIMIT {}", this.limit);
    }
    if (this.offset != null) {
      s.line(" OFFSET {}", this.offset);
    }
    return s.stripTrailingNewLine().toString();
  }

  public List<Object> getParameters() {
    List<Object> params = new ArrayList<Object>();
    for (JoinClause<?, ?> join : this.joins) {
      if (join.getAdditionalJoinCriterion() != null) {
        params.addAll(join.getAdditionalJoinCriterion().getParameters());
      }
    }
    if (this.getWhere() != null) {
      params.addAll(this.getWhere().getParameters());
    }
    if (this.having != null) {
      params.addAll(this.having.getParameters());
    }
    return params;
  }

  @SuppressWarnings("rawtypes")
  private Select<T> join(JoinClause<?, ?> join, boolean addBaseAliases) {
    this.joins.add(join);
    if (join.getAlias() != null && addBaseAliases) {
      Alias<?> base = join.getAlias().getBaseClassAlias();
      while (base != null) {
        IdAliasColumn<?> id = base.getSubClassIdColumn() == null ? base.getIdColumn() : base.getSubClassIdColumn();
        this.join(new JoinClause(join.getType(), base, join.getAlias().getSubClassIdColumn(), id), false);
        base = base.getBaseClassAlias();
      }
    }
    return this;
  }

  @SuppressWarnings("rawtypes")
  private void addOuterJoinsForSubClasses() {
    int i = 0;
    List<String> subClassCases = new ArrayList<String>();
    for (Alias<?> sub : this.from.getSubClassAliases()) {
      this.join(new JoinClause("LEFT OUTER JOIN", sub, this.from.getIdColumn(), sub.getSubClassIdColumn()), false);
      for (AliasColumn<?, ?, ?> c : sub.getColumns()) {
        this.selectItems.add(new SelectItem(c));
      }
      subClassCases.add(0, "WHEN " + sub.getSubClassIdColumn().getQualifiedName() + " IS NOT NULL THEN " + (i++));
    }
    if (i > 0) {
      this.selectItems.add(new SelectItem("CASE " + Join.space(subClassCases) + " ELSE -1 END", "_clazz"));
    }
  }

  @SuppressWarnings("rawtypes")
  private void addInnerJoinsForBaseClasses() {
    Alias<?> base = this.from.getBaseClassAlias();
    while (base != null) {
      List<SelectItem> selectItems = new ArrayList<SelectItem>();
      IdAliasColumn<?> id = base.getSubClassIdColumn() == null ? base.getIdColumn() : base.getSubClassIdColumn();
      this.join(new JoinClause("INNER JOIN", base, this.from.getSubClassIdColumn(), id), false);
      for (AliasColumn<?, ?, ?> c : base.getColumns()) {
        selectItems.add(new SelectItem(c));
      }
      base = base.getBaseClassAlias();
      this.selectItems.addAll(0, selectItems);
    }
  }

  private boolean isLoadingDomainObjects(Class<?> type) {
    return this.from.getDomainClass().isAssignableFrom(type);
  }

}
