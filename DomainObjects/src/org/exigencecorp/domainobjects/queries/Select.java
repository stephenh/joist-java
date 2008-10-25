package org.exigencecorp.domainobjects.queries;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Ids;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.util.Copy;
import org.exigencecorp.util.Join;

public class Select<T extends DomainObject> {

    public static <T extends DomainObject> Select<T> from(Alias<T> alias) {
        return new Select<T>(alias);
    }

    private final Alias<T> from;
    private final List<JoinClause> joins = new ArrayList<JoinClause>();
    private final List<SelectItem> selectItems = new ArrayList<SelectItem>();
    private Where where = null;
    private Order[] orderBy = null;

    private Select(Alias<T> alias) {
        this.from = alias;

        for (AliasColumn<T, ?, ?> c : alias.getColumns()) {
            this.selectItems.add(new SelectItem(c));
        }

        int i = 0;
        List<String> subClassCases = new ArrayList<String>();
        for (Alias<?> sub : alias.getSubClassAliases()) {
            this.join(new JoinClause("LEFT OUTER JOIN", sub, sub.getSubClassIdColumn(), alias.getIdColumn()));
            for (AliasColumn<?, ?, ?> c : sub.getColumns()) {
                this.selectItems.add(new SelectItem(c));
            }
            subClassCases.add("WHEN " + sub.getSubClassIdColumn().getQualifiedName() + " IS NOT NULL THEN " + (i++));
        }
        if (i > 0) {
            this.selectItems.add(new SelectItem("CASE " + Join.space(subClassCases) + " ELSE -1 END AS _clazz"));
        }

        Alias<?> base = alias.getBaseClassAlias();
        while (base != null) {
            IdAliasColumn<?> id = base.getSubClassIdColumn() == null ? base.getIdColumn() : base.getSubClassIdColumn();
            this.join(new JoinClause("INNER JOIN", base, id, alias.getSubClassIdColumn()));
            for (AliasColumn<?, ?, ?> c : base.getColumns()) {
                this.selectItems.add(new SelectItem(c));
            }
            base = base.getBaseClassAlias();
        }
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

    public List<T> list() {
        return this.list(this.from.getDomainClass());
    }

    public T unique() {
        return this.unique(this.from.getDomainClass());
    }

    public <R> List<R> list(Class<R> rowType) {
        return UoW.getCurrent().getRepository().select(this, rowType);
    }

    public <R> R unique(Class<R> rowType) {
        List<R> results = this.list(rowType);
        if (results.size() == 0) {
            throw new RuntimeException("Not found");
        } else if (results.size() > 1) {
            throw new RuntimeException("Too many");
        }
        return results.get(0);
    }

    public Ids<T> listIds() {
        return UoW.getCurrent().getRepository().selectIds(this);
    }

    public long count() {
        this.select(new SelectItem("count(distinct " + this.from.getIdColumn().getQualifiedName() + ") as count"));
        return this.unique(Count.class).count;
    }

    public static class Count {
        public Long count;
    }

    public Alias<T> getFrom() {
        return this.from;
    }

    public List<JoinClause> getJoins() {
        return this.joins;
    }

    public List<SelectItem> getSelectItems() {
        return this.selectItems;
    }

    public Where getWhere() {
        return this.where;
    }

    public Order[] getOrderBy() {
        return this.orderBy;
    }

}
