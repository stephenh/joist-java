package org.exigencecorp.domainobjects.codegen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public abstract class CodegenConfig {

    private Map<String, String> javaTypeByDataType = new HashMap<String, String>();
    private Map<String, String> javaTypeByTableAndColumn = new HashMap<String, String>();
    private Map<String, String> hibernateTypeByJavaType = new HashMap<String, String>();
    private Map<String, String> getterAccessByTableAndColumn = new HashMap<String, String>();
    private Map<String, String> setterAccessByTableAndColumn = new HashMap<String, String>();
    private List<String> doNotIncrementParentsOpLock = new ArrayList<String>();
    private List<String> skipCollection = new ArrayList<String>();
    private List<String> notAbstractEvenThoughSubclassed = new ArrayList<String>();
    private Map<String, List<String>> customRulesByJavaType = new HashMap<String, List<String>>();
    private String projectNameForDefaults = "project";

    protected CodegenConfig() {
        this.setJavaType("integer", "Integer");
        this.setJavaType("character", "String");
        this.setJavaType("character varying", "String");
        this.setJavaType("text", "String");
        this.setJavaType("smallint", "Short");
        this.setJavaType("bigint", "Long");
        this.setJavaType("boolean", "boolean");
        this.setJavaType("bytea", "byte[]");
        this.setJavaType("date", "java.sql.Date");
        this.setHibernateType("byte[]", "binary");
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

    public void setJavaType(String dataType, String javaType) {
        this.javaTypeByDataType.put(dataType, javaType);
    }

    public void setJavaType(String tableName, String columnName, String javaType) {
        this.javaTypeByTableAndColumn.put(tableName + "." + columnName, javaType);
    }

    public String getJavaType(String tableName, String columnName, String dataType) {
        String key = tableName + "." + columnName;
        if (this.javaTypeByTableAndColumn.containsKey(key)) {
            return this.javaTypeByTableAndColumn.get(key);
        }
        if (this.javaTypeByDataType.containsKey(dataType)) {
            return this.javaTypeByDataType.get(dataType);
        }
        throw new RuntimeException("Unmatched data type: " + dataType);
    }

    public void setHibernateType(String javaType, String hibernateType) {
        this.hibernateTypeByJavaType.put(javaType, hibernateType);
    }

    public String getHibernateType(String javaType) {
        if (this.hibernateTypeByJavaType.containsKey(javaType)) {
            return this.hibernateTypeByJavaType.get(javaType);
        } else {
            return javaType.toLowerCase();
        }
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
