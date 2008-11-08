package org.exigencecorp.domainobjects.queries;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.StringBuilderr;

public class Delete<T extends DomainObject> {

    public static <T extends DomainObject> Delete<T> from(Alias<T> alias) {
        return new Delete<T>(alias);
    }

    private final Alias<T> alias;
    private final List<List<Object>> allParameters = new ArrayList<List<Object>>();
    private Where where = null;

    private Delete(Alias<T> alias) {
        this.alias = alias;
        this.allParameters.add(new ArrayList<Object>());
    }

    public void execute() {
        Jdbc.updateAll(UoW.getCurrent().getRepository().getConnection(), this.toSql(), this.getAllParameters());
    }

    public Alias<T> getAlias() {
        return this.alias;
    }

    public Delete<T> where(Where where) {
        if (this.where != null) {
            throw new RuntimeException("Already set");
        }
        this.where = where;
        this.where.stripLeadingAliasForUpdates(this.getAlias().getName());
        this.allParameters.get(0).addAll(where.getParameters());
        return this;
    }

    public String toSql() {
        StringBuilderr s = new StringBuilderr();
        s.append("DELETE FROM ");
        s.line(this.getAlias().getTableName());
        if (this.where != null) {
            s.line(" WHERE {}", this.where);
        }
        s.stripTrailingNewLine();
        return s.toString();
    }

    public List<List<Object>> getAllParameters() {
        return this.allParameters;
    }

}
