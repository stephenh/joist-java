package org.exigencecorp.domainobjects.orm.repos.sql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.queries.SetItem;
import org.exigencecorp.domainobjects.queries.Update;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.StringBuilderr;

public class Updater<T extends DomainObject> {

    private Connection connection;
    private Update<T> update;

    public Updater(Connection connection, Update<T> update) {
        this.connection = connection;
        this.update = update;
    }

    public void update() {
        Jdbc.update(this.connection, this.toSql(), this.getParameters());
    }

    public String toSql() {
        StringBuilderr s = new StringBuilderr();
        s.line("UPDATE {} SET", this.update.getAlias().getTableName());
        s.append(" ");
        for (SetItem item : this.update.getSetItems()) {
            s.append("{} = ?, ", item.getColumn().getName());
        }
        s.stripLastCommaSpace();
        if (this.update.getWhere() != null) {
            s.line(" WHERE {}", this.update.getWhere());
        }
        s.stripTrailingNewLine();
        return StringUtils.replace(s.toString(), this.update.getAlias().getName() + ".", "");
    }

    public List<Object> getParameters() {
        List<Object> parameters = new ArrayList<Object>();
        for (SetItem item : this.update.getSetItems()) {
            parameters.add(item.getValue());
        }
        if (this.update.getWhere() != null) {
            parameters.addAll(this.update.getWhere().getParameters());
        }
        return parameters;
    }

}
