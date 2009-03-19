package joist.domain.queries.columns;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.queries.Alias;
import joist.domain.queries.JoinClause;
import joist.domain.queries.Where;

/**
 * @param T the domain object the column is within
 * @param W the domain object the column points to
 */
public class ForeignKeyAliasColumn<T extends DomainObject, W extends DomainObject> extends AliasColumn<T, Integer, Integer> {

    public ForeignKeyAliasColumn(Alias<T> alias, String name, Shim<T, Integer> shim) {
        super(alias, name, shim);
    }

    public Where equals(W value) {
        return new Where(this.getQualifiedName() + " = ?", value.getId());
    }

    public Where equals(Integer value) {
        return new Where(this.getQualifiedName() + " = ?", value);
    }

    public JoinClause on(Alias<W> on) {
        return new JoinClause("INNER JOIN", this.getAlias(), on.getIdColumn(), this);
    }

    public JoinClause on(IdAliasColumn<W> on) {
        return new JoinClause("INNER JOIN", this.getAlias(), on, this);
    }

}
