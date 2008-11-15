package org.exigencecorp.domainobjects.queries;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.StringBuilderr;

public class Update<T extends DomainObject> {

    private final String aliasName;
    private final String tableName;
    private final List<String> columnNames = new ArrayList<String>();
    private final List<List<Object>> allParameters = new ArrayList<List<Object>>();
    private final boolean isTemplate;
    private Where where = null;

    public static <T extends DomainObject> Update<T> into(Alias<T> alias) {
        return new Update<T>(alias, false);
    }

    public static <T extends DomainObject> Update<T> intoTemplate(Alias<T> alias) {
        return new Update<T>(alias, true);
    }

    private Update(Alias<T> alias, boolean isTemplate) {
        this.aliasName = alias.getName();
        this.tableName = alias.getTableName();
        this.isTemplate = isTemplate;
        if (!isTemplate) {
            this.allParameters.add(new ArrayList<Object>());
        }
    }

    public List<Integer> execute() {
        return Jdbc.updateAll(UoW.getConnection(), this.toSql(), this.getAllParameters());
    }

    public void set(SetItem setItem) {
        this.columnNames.add(setItem.getColumn().getName());
        if (!this.isTemplate) {
            this.allParameters.get(0).add(setItem.getValue());
        }
    }

    public void where(Where where) {
        if (this.where != null) {
            throw new RuntimeException("Already set");
        }
        this.where = where;
        this.where.stripLeadingAliasForUpdates(this.aliasName);
        if (!this.isTemplate) {
            this.allParameters.get(0).addAll(where.getParameters());
        }
    }

    public void addColumnName(String name) {
        this.columnNames.add(name);
    }

    public void addMoreParameters(List<Object> parameters) {
        this.allParameters.add(parameters);
    }

    public List<List<Object>> getAllParameters() {
        return this.allParameters;
    }

    public String toSql() {
        StringBuilderr s = new StringBuilderr();
        s.line("UPDATE {}", this.tableName);
        s.append(" SET ");
        for (String columnName : this.columnNames) {
            s.append(columnName);
            s.append(" = ?, ");
        }
        s.stripLastCommaSpace();
        s.line();
        if (this.where != null) {
            s.line(" WHERE {}", this.where.getSql());
        }
        s.stripTrailingNewLine();
        return s.toString();
    }

}
