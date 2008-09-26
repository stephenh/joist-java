package org.exigencecorp.domainobjects.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.codegen.InformationSchemaWrapper;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.jdbc.RowMapper;
import org.exigencecorp.util.Copy;
import org.exigencecorp.util.Log;

public class Schema {

    private InformationSchemaWrapper informationSchema;
    private DataSource ds;
    private List<String> tableNames;
    private List<String> viewNames;
    private List<String> sequenceNames;
    private List<String> functionNames;
    private List<String> codeTables;
    private List<String> systemDataTableNames;
    private List<String> systemDataSequenceNames;

    public Schema(DataSource ds) {
        this.ds = ds;
        this.informationSchema = new InformationSchemaWrapper(ds);
        this.load();
    }

    private void load() {
        String sql = "select pg_class.relname from pg_class, pg_namespace"
            + " where pg_class.relkind = 'r'"
            + " and pg_namespace.nspname = 'public'"
            + " and pg_class.relnamespace = pg_namespace.oid"
            + " order by pg_class.relname";
        this.tableNames = this.getNames(sql);

        sql = "select pg_class.relname from pg_class, pg_namespace"
            + " where pg_class.relkind = 'v'"
            + " and pg_namespace.nspname = 'public'"
            + " and pg_class.relnamespace = pg_namespace.oid"
            + " order by pg_class.relname";
        this.viewNames = this.getNames(sql);

        sql = "SELECT relname FROM pg_class WHERE relkind = 'S' ORDER BY pg_class.relname";
        this.sequenceNames = this.getNames(sql);

        sql = "SELECT proname, proargtypes FROM pg_proc, pg_namespace"
            + " WHERE pg_namespace.nspname = 'public'"
            + " AND pg_proc.pronamespace = pg_namespace.oid"
            + " AND pg_proc.proisagg=false"
            + " and proname not like 'pl%'"
            + " ORDER BY proname";
        this.functionNames = this.getFunctionNames(sql);

        this.codeTables = new ArrayList<String>();
        for (String tableName : this.tableNames) {
            if (this.informationSchema.isCodeTable(tableName)) {
                this.codeTables.add(tableName);
            }
        }

        this.systemDataTableNames = new ArrayList<String>();
        this.systemDataTableNames.addAll(this.codeTables);
        this.systemDataTableNames.add("schema_info");
        this.systemDataTableNames.add("code_id");

        this.systemDataSequenceNames = new ArrayList<String>();
        // Include one-off sequences that do not have tables
        this.systemDataSequenceNames.add("constraint_name_seq");
        // Include sequences of system data tables (if they have a sequence)
        for (String tableName : this.systemDataTableNames) {
            if (this.sequenceNames.contains(tableName + "_id_seq")) {
                this.systemDataSequenceNames.add(tableName + "_id_seq");
            }
        }
    }

    public void reload() {
        Log.info("Reloading schema");
        this.load();
    }

    public List<String> getTableNames() {
        return Copy.shallow(this.tableNames);
    }

    public List<String> getViewNames() {
        return Copy.shallow(this.viewNames);
    }

    public List<String> getTableAndViewNames() {
        return Copy.union(this.getTableNames(), this.getViewNames());
    }

    public List<String> getCodeTableNames() {
        return Copy.shallow(this.codeTables);
    }

    public List<String> getSequenceNames() {
        return Copy.shallow(this.sequenceNames);
    }

    public List<String> getFunctionNames() {
        return Copy.shallow(this.functionNames);
    }

    public List<String> getSystemDataTableNames() {
        return Copy.shallow(this.systemDataTableNames);
    }

    public List<String> getSystemDataSequenceNames() {
        return Copy.shallow(this.systemDataSequenceNames);
    }

    public List<String> getNonSystemDataTableNames() {
        return ListUtils.removeAll(this.getTableNames(), this.getSystemDataTableNames());
    }

    public List<String> getNonSystemDataSequenceNames() {
        return ListUtils.removeAll(this.getSequenceNames(), this.getSystemDataSequenceNames());
    }

    private List<String> getNames(String sql) {
        final List<String> names = new ArrayList<String>();
        Jdbc.query(this.ds, sql, new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                names.add(rs.getString(1));
            }
        });
        return names;
    }

    private List<String> getFunctionNames(String sql) {
        final Map<Integer, String> typeIdToName = new HashMap<Integer, String>();
        Jdbc.query(this.ds, "SELECT oid, typname FROM pg_type", new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                typeIdToName.put(rs.getInt(1), rs.getString(2));
            }
        });

        final List<String> names = new ArrayList<String>();
        Jdbc.query(this.ds, sql, new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                String name = rs.getString(1);
                List<String> params = new ArrayList<String>();
                for (String id : StringUtils.split(rs.getString(2), ' ')) {
                    params.add(typeIdToName.get(Integer.parseInt(id)));
                }
                names.add(name + "(" + StringUtils.join(params, ",") + ")");
            }
        });

        return names;
    }

}
