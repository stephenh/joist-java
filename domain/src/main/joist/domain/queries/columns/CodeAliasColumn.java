package joist.domain.queries.columns;


import joist.domain.Code;
import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.queries.Alias;

/**
 * @param T the domain object the column is within
 * @param W the domain object the column points to
 */
public class CodeAliasColumn<T extends DomainObject, W extends Code> extends AliasColumn<T, Integer, Integer> {

    public CodeAliasColumn(Alias<T> alias, String name, Shim<T, Integer> shim) {
        super(alias, name, shim);
    }

}
