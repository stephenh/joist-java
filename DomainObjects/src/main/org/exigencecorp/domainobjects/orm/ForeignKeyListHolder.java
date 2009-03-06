package org.exigencecorp.domainobjects.orm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.queries.columns.ForeignKeyAliasColumn;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.util.Copy;

/**
 * A value holder that will lazy load the objects inferred by their foreign key to us.
 *
 * Until <code>loaded</code> is fetched from the database (if the unit of work is
 * open), we keep track of adds/removes and then only if truly asked for the entire
 * collection do we integrate the database results with the captured adds/removes.
 *
 * @param T the parent domain object
 * @param U the child domain object (the one with the foreign key in it)
 */
public class ForeignKeyListHolder<T extends DomainObject, U extends DomainObject> {

    private final T parent;
    private final Alias<U> childAlias;
    private final ForeignKeyAliasColumn<U, T> childForeignKeyToParentColumn;
    private final List<U> addedBeforeLoaded = new ArrayList<U>();
    private final List<U> removedBeforeLoaded = new ArrayList<U>();
    private List<U> loaded;
    private List<U> readOnly;

    public ForeignKeyListHolder(T parent, Alias<U> childAlias, ForeignKeyAliasColumn<U, T> childForeignKeyToParentColumn) {
        this.parent = parent;
        this.childAlias = childAlias;
        this.childForeignKeyToParentColumn = childForeignKeyToParentColumn;
    }

    public List<U> get() {
        if (this.loaded == null) {
            if (UoW.isOpen() && this.parent.getId() != null) {
                this.loaded = Select.from(this.childAlias).where(//
                    this.childForeignKeyToParentColumn.equals(this.parent)).orderBy(this.childAlias.getIdColumn().asc()).list();
            } else {
                this.loaded = new ArrayList<U>();
            }
            if (this.addedBeforeLoaded.size() > 0 || this.removedBeforeLoaded.size() > 0) {
                this.loaded.addAll(this.addedBeforeLoaded);
                this.loaded.removeAll(this.removedBeforeLoaded);
                this.loaded = Copy.unique(this.loaded);
            }
            this.readOnly = Collections.unmodifiableList(this.loaded);
        }
        return this.readOnly;
    }

    public void add(U instance) {
        if (this.loaded == null) {
            this.removedBeforeLoaded.remove(instance);
            this.addedBeforeLoaded.add(instance);
        } else {
            this.loaded.add(instance);
        }
    }

    public void remove(U instance) {
        if (this.loaded == null) {
            this.addedBeforeLoaded.remove(instance);
            this.removedBeforeLoaded.add(instance);
        } else {
            this.loaded.remove(instance);
        }
    }

}
