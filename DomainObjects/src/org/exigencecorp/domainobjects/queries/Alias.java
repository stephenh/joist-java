package org.exigencecorp.domainobjects.queries;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.queries.columns.AliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;

/**
 * @param T the domain class to query against
 */
public abstract class Alias<T extends DomainObject> {

    private final Class<T> domainClass;
    private final Class<? super T> domainBaseClass;
    private final String tableName;
    private final String name;
    private final List<Alias<? extends T>> subClassAliases = new ArrayList<Alias<? extends T>>();

    protected Alias(Class<T> domainClass, Class<? super T> domainBaseClass, String tableName, String name) {
        this.domainClass = domainClass;
        this.domainBaseClass = domainBaseClass;
        this.tableName = tableName;
        this.name = name;
    }

    public abstract List<AliasColumn<T, ?, ?>> getColumns();

    public abstract IdAliasColumn<? super T> getIdColumn();

    public abstract IntAliasColumn<? super T> getVersionColumn();

    public abstract IdAliasColumn<T> getSubClassIdColumn();

    public String getTableName() {
        return this.tableName;
    }

    public String getName() {
        return this.name;
    }

    protected void addSubClassAlias(Alias<? extends T> subClassAlias) {
        this.subClassAliases.add(subClassAlias);
    }

    public List<Alias<? extends T>> getSubClassAliases() {
        return this.subClassAliases;
    }

    /** @return the current domain class
     *
     * In a 3-level inheritance hierarchy, this would be the 3rd level.
     */
    public Class<T> getDomainClass() {
        return this.domainClass;
    }

    /** @return the immediate super domain class--could be null
     *
     * In a 3-level inheritance hierarchy, this would be the 2nd level.
     */
    public Class<? super T> getDomainBaseClass() {
        return this.domainBaseClass;
    }

    /** @return the immediate super domain alias--could be null
     *
     * In a 3-level inheritance hierarchy, this would be the 2nd level.
     */
    public Alias<? super T> getBaseClassAlias() {
        return null;
    }

    /** @return the root domain alias--never null
     *
     * In a 3-level inheritance hierarchy, this would be the 1st level.
     */
    public final Alias<? super T> getRootClassAlias() {
        Alias<? super T> current = this;
        while (current.getBaseClassAlias() != null) {
            current = current.getBaseClassAlias();
        }
        return current;
    }

    public boolean isRootClass() {
        return this.getBaseClassAlias() == null;
    }

}
