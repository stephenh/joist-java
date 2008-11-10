package org.exigencecorp.domainobjects.codegen;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.exigencecorp.domainobjects.queries.columns.BooleanAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.DateAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IntAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.StringAliasColumn;

public abstract class CodegenConfig {

    private Map<String, Class<?>> javaTypeByDataType = new HashMap<String, Class<?>>();
    private Map<String, Class<?>> aliasTypeByDataType = new HashMap<String, Class<?>>();
    private Map<String, String> getterAccessByTableAndColumn = new HashMap<String, String>();
    private Map<String, String> setterAccessByTableAndColumn = new HashMap<String, String>();
    private List<String> doNotIncrementParentsOpLock = new ArrayList<String>();
    private List<String> skipCollection = new ArrayList<String>();
    private List<String> notAbstractEvenThoughSubclassed = new ArrayList<String>();
    private Map<String, List<String>> customRulesByJavaType = new HashMap<String, List<String>>();
    private String projectNameForDefaults = "project";

    protected CodegenConfig() {
        this.setJavaType("integer", Integer.class, IntAliasColumn.class);
        this.setJavaType("character", String.class, StringAliasColumn.class);
        this.setJavaType("character varying", String.class, StringAliasColumn.class);
        this.setJavaType("text", String.class, StringAliasColumn.class);
        this.setJavaType("smallint", Short.class, null);
        this.setJavaType("bigint", Long.class, null);
        this.setJavaType("boolean", boolean.class, BooleanAliasColumn.class);
        this.setJavaType("bytea", byte[].class, null);
        this.setJavaType("date", Date.class, DateAliasColumn.class);
    }

    protected CodegenConfig(String projectNameForDefaults) {
        this();
        this.projectNameForDefaults = projectNameForDefaults;
    }

    /**
     * A connection to your database.
     */
    public abstract DataSource getDataSource();

    /**
     * Where the generated-once subclasses (e.g. Employee/EmployeeMapper)
     * that you add business logic go.
     * 
     * @return E.g. <code>src/main</code>
     */
    public String getOutputSourceDirectory() {
        return "./src/main";
    }

    /**
     * Where the re-generated base classes (e.g. EmployeeCodegen) that
     * you do not edit go.
     * 
     * @return E.g. <code>src/codegen</code>
     */
    public String getOutputCodegenDirectory() {
        return "./src/codegen";
    }

    /**
     * The package name of your domain objects.
     *
     * @return E.g. <code>cbas.domain</code>
     */
    public String getDomainObjectPackage() {
        return this.projectNameForDefaults + ".domain";
    }

    /**
     * The package name of your mapper objects.
     *
     * @return E.g. <code>cbas.domain.mappers</code>
     */
    public String getMapperPackage() {
        return this.projectNameForDefaults + ".domain.mappers";
    }

    public String getDomainObjectBaseClass() {
        return "org.exigencecorp.domainobjects.AbstractDomainObject";
    }

    public void setJavaType(String jdbcDataType, Class<?> javaType, Class<?> aliasColumnType) {
        this.javaTypeByDataType.put(jdbcDataType, javaType);
        this.aliasTypeByDataType.put(jdbcDataType, aliasColumnType);
    }

    public String getJavaType(String tableName, String columnName, String dataType) {
        if (this.javaTypeByDataType.containsKey(dataType)) {
            return this.javaTypeByDataType.get(dataType).getName();
        }
        throw new RuntimeException("Unmatched data type: " + dataType);
    }

    public Class<?> getAliasType(String tableName, String columnName, String dataType) {
        if ("id".equals(columnName)) {
            return IdAliasColumn.class;
        }
        if (this.aliasTypeByDataType.containsKey(dataType)) {
            return this.aliasTypeByDataType.get(dataType);
        }
        throw new RuntimeException("Unmatched data type: " + dataType);
    }

    public void setGetterAccess(String tableName, String columnName, String access) {
        this.getterAccessByTableAndColumn.put(tableName + "." + columnName, access);
    }

    public String getGetterAccess(String tableName, String columnName) {
        String key = tableName + "." + columnName;
        if (this.getterAccessByTableAndColumn.containsKey(key)) {
            return this.getterAccessByTableAndColumn.get(key);
        } else {
            return "public";
        }
    }

    public void setSetterAccess(String tableName, String columnName, String access) {
        this.setterAccessByTableAndColumn.put(tableName + "." + columnName, access);
    }

    public String getSetterAccess(String tableName, String columnName) {
        String key = tableName + "." + columnName;
        if (this.setterAccessByTableAndColumn.containsKey(key)) {
            return this.setterAccessByTableAndColumn.get(key);
        } else {
            return "public";
        }
    }

    public void setNotAbstractEvenThoughSubclassed(String tableName) {
        this.notAbstractEvenThoughSubclassed.add(tableName);
    }

    public boolean isNotAbstractEvenThoughSubclassed(String tableName) {
        return this.notAbstractEvenThoughSubclassed.contains(tableName);
    }

    public void setCollectionSkipped(String objectName, String variableName) {
        this.skipCollection.add(objectName + "." + variableName);
    }

    public boolean isCollectionSkipped(String objectName, String variableName) {
        return this.skipCollection.contains(objectName + "." + variableName);
    }

    public void setDoNotIncrementParentsOpLock(String objectName, String variableName) {
        this.doNotIncrementParentsOpLock.add(objectName + "." + variableName);
    }

    public void setDoNotIncrementParentsOpLock(String objectName) {
        this.doNotIncrementParentsOpLock.add(objectName);
    }

    public boolean isDoNotIncrementParentsOpLock(String objectName, String variableName) {
        return this.doNotIncrementParentsOpLock.contains(objectName) || this.doNotIncrementParentsOpLock.contains(objectName + "." + variableName);
    }

    public void addCustomRule(String javaType, String rule) {
        List<String> rules = this.customRulesByJavaType.get(javaType);
        if (rules == null) {
            rules = new ArrayList<String>();
            this.customRulesByJavaType.put(javaType, rules);
        }
        rules.add(rule);
    }

    public List<String> getCustomRules(String entityType, String javaType, String variableName) {
        if (!this.customRulesByJavaType.containsKey(javaType)) {
            return new ArrayList<String>();
        }
        List<String> rendered = new ArrayList<String>();
        for (String rule : this.customRulesByJavaType.get(javaType)) {
            rendered.add(rule.replaceAll("\\$variableName", variableName).replaceAll("\\$entityType", entityType));
        }
        return rendered;
    }

}
