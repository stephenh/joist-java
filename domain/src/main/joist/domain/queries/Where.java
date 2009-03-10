package joist.domain.queries;

import java.util.List;

import org.exigencecorp.util.Copy;

public class Where {

    private String sql;
    private List<Object> parameters;

    private static Where makeAnd(Where... clauses) {
        String sql = clauses[0].sql;
        List<Object> parameters = Copy.list(clauses[0].parameters);
        for (int i = 1; i < clauses.length; i++) {
            sql += "\n AND " + clauses[i].sql;
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
        return Where.makeAnd(this, other);
    }

    public void stripLeadingAliasForUpdates(String aliasName) {
        this.sql = this.sql.replaceAll(aliasName + "\\.", "");
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

}
