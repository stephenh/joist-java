package joist.domain.orm.queries;

import java.util.List;

import joist.util.Copy;

public class Where {

    private final String sql;
    private final List<Object> parameters;

    public static Where not(Where clause) {
        return new Where("NOT (\n " + clause.sql + ")", clause.parameters);
    }

    private static Where make(String operator, Where... clauses) {
        String sql = clauses[0].sql;
        List<Object> parameters = Copy.list(clauses[0].parameters);
        for (int i = 1; i < clauses.length; i++) {
            sql += "\n " + operator + " " + clauses[i].sql;
            parameters.addAll(clauses[i].parameters);
        }
        return new Where(sql, parameters);
    }

    public Where(String sql, Object... parameters) {
        this.sql = sql;
        this.parameters = Copy.list(parameters);
    }

    public Where(String sql, List<Object> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    public Where and(Where other) {
        return Where.make("AND", this, other);
    }

    public Where or(Where other) {
        return Where.make("OR", this, other);
    }

    public String toString() {
        return this.sql;
    }

    public List<Object> getParameters() {
        return this.parameters;
    }

    public String getSql() {
        return this.sql;
    }

    public String getSqlWithoutAliasPrefix(String aliasName) {
        return this.sql.replaceAll(aliasName + "\\.", "");
    }

}
