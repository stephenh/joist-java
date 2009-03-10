package joist.domain.queries;

import java.util.ArrayList;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.Requirements;
import joist.domain.exceptions.NotFoundException;
import joist.domain.exceptions.TooManyException;
import joist.domain.orm.mappers.DataTransferObjectMapper;
import joist.domain.orm.mappers.DomainObjectMapper;
import joist.domain.orm.mappers.IdsMapper;
import joist.domain.queries.columns.AliasColumn;
import joist.domain.queries.columns.IdAliasColumn;
import joist.domain.uow.UoW;


import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.jdbc.RowMapper;
import org.exigencecorp.util.Copy;
import org.exigencecorp.util.Join;
import org.exigencecorp.util.StringBuilderr;
import org.exigencecorp.util.Wrap;

public class Select<T extends DomainObject> {

    public static <T extends DomainObject> Select<T> from(Alias<T> alias) {
        return new Select<T>(alias);
    }

    private final Alias<T> from;
    private final List<JoinClause> joins = new ArrayList<JoinClause>();
    private final List<SelectItem> selectItems = new ArrayList<SelectItem>();
    private Where where = null;
    private Order[] orderBy = null;
    private Integer limit;
    private Integer offset;

    private Select(Alias<T> alias) {
        this.from = alias;
        for (AliasColumn<T, ?, ?> c : alias.getColumns()) {
            this.selectItems.add(new SelectItem(c));
        }
        this.addInnerJoinsForBaseClasses();
        this.addOuterJoinsForSubClasses();
    }

    public void join(JoinClause join) {
        this.joins.add(join);
    }

    public void select(SelectItem... selectItems) {
        this.selectItems.clear();
        this.selectItems.addAll(Copy.list(selectItems));
    }

    public Select<T> where(Where where) {
        this.where = where;
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

    public <R> List<R> list(Class<R> rowType) {
        final List<R> results = new ArrayList<R>();
        RowMapper mapper = null;
        if (this.isLoadingDomainObjects(rowType)) {
            mapper = new DomainObjectMapper<T>(this.from, (List<T>) results);
        } else {
            mapper = new DataTransferObjectMapper<T, R>(rowType, results);
        }
        Jdbc.query(UoW.getConnection(), this.toSql(), this.getParameters(), mapper);
        return results;
    }

    public <R> R unique(Class<R> rowType) {
        List<R> results = this.list(rowType);
        if (results.size() == 0) {
            Requirements.selectUniqueWithTooManyFails.fulfills();
            throw new NotFoundException();
        } else if (results.size() > 1) {
            Requirements.selectUniqueWithTooManyFails.fulfills();
            throw new TooManyException();
        }
        return results.get(0);
    }

    public List<Integer> listIds() {
        List<Integer> ids = new ArrayList<Integer>();
        Jdbc.query(UoW.getConnection(), this.toSql(), this.getParameters(), new IdsMapper<T>(this.from, ids));
        return ids;
    }

    public long count() {
        // Make a copy countQuery so we can discard any select items and order bys and not mess up this query
        Select<T> countQuery = Select.from(this.from);
        countQuery.joins.addAll(this.joins);
        countQuery.where = this.where;
        countQuery.offset = this.offset;
        countQuery.limit = this.limit;
        countQuery.select(new SelectItem("count(distinct " + this.from.getIdColumn().getQualifiedName() + ") as count"));
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

    public String toSql() {
        StringBuilderr s = new StringBuilderr();
        s.line("SELECT {}", Join.commaSpace(this.selectItems));
        s.line(" FROM {} {}", Wrap.quotes(this.from.getTableName()), this.from.getName());
        for (JoinClause join : this.joins) {
            s.line(" {}", join);
        }
        if (this.getWhere() != null) {
            s.line(" WHERE {}", this.getWhere());
        }
        if (this.getOrderBy() != null) {
            s.line(" ORDER BY {}", Join.commaSpace(this.getOrderBy()));
        }
        Requirements.selectLimitAndOffset.fulfills();
        if (this.limit != null) {
            s.line(" LIMIT {}", this.limit);
        }
        if (this.offset != null) {
            s.line(" OFFSET {}", this.offset);
        }
        return s.stripTrailingNewLine().toString();
    }

    public List<Object> getParameters() {
        if (this.getWhere() != null) {
            return this.getWhere().getParameters();
        }
        return new ArrayList<Object>();
    }

    private void addOuterJoinsForSubClasses() {
        int i = 0;
        List<String> subClassCases = new ArrayList<String>();
        for (Alias<?> sub : this.from.getSubClassAliases()) {
            this.join(new JoinClause("LEFT OUTER JOIN", sub, sub.getSubClassIdColumn(), this.from.getIdColumn()));
            for (AliasColumn<?, ?, ?> c : sub.getColumns()) {
                this.selectItems.add(new SelectItem(c));
            }
            subClassCases.add(0, "WHEN " + sub.getSubClassIdColumn().getQualifiedName() + " IS NOT NULL THEN " + (i++));
        }
        if (i > 0) {
            this.selectItems.add(new SelectItem("CASE " + Join.space(subClassCases) + " ELSE -1 END AS _clazz"));
        }
    }

    private void addInnerJoinsForBaseClasses() {
        Alias<?> base = this.from.getBaseClassAlias();
        while (base != null) {
            IdAliasColumn<?> id = base.getSubClassIdColumn() == null ? base.getIdColumn() : base.getSubClassIdColumn();
            this.join(new JoinClause("INNER JOIN", base, id, this.from.getSubClassIdColumn()));
            for (AliasColumn<?, ?, ?> c : base.getColumns()) {
                this.selectItems.add(new SelectItem(c));
            }
            base = base.getBaseClassAlias();
        }
    }

    private boolean isLoadingDomainObjects(Class<?> type) {
        return this.from.getDomainClass().isAssignableFrom(type);
    }

}
