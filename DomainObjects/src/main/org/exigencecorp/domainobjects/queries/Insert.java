package org.exigencecorp.domainobjects.queries;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.Join;
import org.exigencecorp.util.StringBuilderr;
import org.exigencecorp.util.Wrap;

public class Insert<T extends DomainObject> {

    public static <T extends DomainObject> Insert<T> into(Alias<T> alias) {
        return new Insert<T>(alias, false);
    }

    public static <T extends DomainObject> Insert<T> intoTemplate(Alias<T> alias) {
        return new Insert<T>(alias, true);
    }

    private final String tableName;
    private final List<String> columnNames = new ArrayList<String>();
    private final List<List<Object>> allParameters = new ArrayList<List<Object>>();
    private final boolean isTemplate;

    private Insert(Alias<T> alias, boolean isTemplate) {
        this.tableName = alias.getTableName();
        this.isTemplate = isTemplate;
        if (!this.isTemplate) {
            this.allParameters.add(new ArrayList<Object>());
        }
    }

    public List<Integer> execute() {
        return Jdbc.updateAll(UoW.getConnection(), this.toSql(), this.getAllParameters());
    }

    public void set(SetItem setItem) {
        this.columnNames.add(setItem.getColumn().getName());
        this.allParameters.get(0).add(setItem.getValue());
    }

    public String getTableName() {
        return this.tableName;
    }

    public List<String> getColumnNames() {
        return this.columnNames;
    }

    public void addColumnName(String name) {
        this.columnNames.add(name);
    }

    public List<List<Object>> getAllParameters() {
        return this.allParameters;
    }

    public void addMoreParameters(List<Object> parameters) {
        this.allParameters.add(parameters);
    }

    public String toSql() {
        StringBuilderr s = new StringBuilderr();
        s.append("INSERT INTO ");
        s.append(Wrap.quotes(this.getTableName()));
        s.append(" (");
        s.append(Join.commaSpace(Wrap.quotes(this.getColumnNames())));
        s.append(")");
        s.append(" VALUES (");
        s.append(StringUtils.repeat("?, ", this.getColumnNames().size()));
        s.stripLastCommaSpace();
        s.append(")");
        return s.toString();
    }

}
