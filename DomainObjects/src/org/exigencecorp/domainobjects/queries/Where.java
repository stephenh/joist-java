package org.exigencecorp.domainobjects.queries;

import java.util.List;

import org.exigencecorp.util.Copy;

public class Where {

    private final String sql;
    private final List<Object> parameters;

    public Where(String sql, Object... parameters) {
        this.sql = sql;
        this.parameters = Copy.list(parameters);
    }

    public Where(String sql, List<Object> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    public static Where and(Where... clauses) {
        String sql = clauses[0].sql;
        List<Object> parameters = Copy.list(clauses[0].parameters);
        for (int i = 1; i < clauses.length; i++) {
            sql += "\n AND " + clauses[i].sql;
            parameters.addAll(clauses[i].parameters);
        }
        return new Where(sql, parameters);

    }

    public String toString() {
        return this.sql;
    }

    public List<Object> getParameters() {
        return this.parameters;
    }

}
