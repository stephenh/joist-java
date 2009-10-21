package joist.domain.codegen;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joist.domain.AbstractDomainObject;
import joist.domain.AbstractQueries;
import joist.domain.orm.queries.columns.BooleanAliasColumn;
import joist.domain.orm.queries.columns.ByteArrayAliasColumn;
import joist.domain.orm.queries.columns.DateAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.IntAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.domain.orm.queries.columns.ShortAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;

public class CodegenConfig {

    /** Where the generated-once subclasses (e.g. Employee) that you add business logic go. @return E.g. <code>src/main</code> */
    public String outputSourceDirectory = "./src/main";

    /** Where the re-generated base classes (e.g. EmployeeCodegen) that you do not edit go. @return E.g. <code>src/codegen</code> */
    public String outputCodegenDirectory = "./src/codegen";

    /** The package name of your domain objects. @return E.g. <code>cbas.domain</code> */
    public String domainObjectPackage = "project.domain";

    /** The package name of your query objects. @return E.g. <code>cbas.domain.queries</code> */
    public String queriesPackage = "project.domain.queries";

    /** The base class for all the generated base classes (e.g. EmployeeCodegen). @return E.g. <code>YourAbstractDomainObject</code> */
    public String domainObjectBaseClass = AbstractDomainObject.class.getName();

    /** The base class for the once-touched queries objects. */
    public String queriesBaseClass = AbstractQueries.class.getName() + "<{}>";

    // Private structures
    private final Map<String, String> javaTypeByDataType = new HashMap<String, String>();
    private final Map<String, String> javaTypeByColumnName = new HashMap<String, String>();
    private final Map<String, String> aliasTypeByDataType = new HashMap<String, String>();
    private final Map<String, String> aliasTypeByColumnName = new HashMap<String, String>();
    private final Map<String, String> getterAccessByTableAndColumn = new HashMap<String, String>();
    private final Map<String, String> setterAccessByTableAndColumn = new HashMap<String, String>();
    private final List<String> doNotIncrementParentsOpLock = new ArrayList<String>();
    private final List<String> skipCollection = new ArrayList<String>();
    private final List<String> notAbstractEvenThoughSubclassed = new ArrayList<String>();
    private final Map<String, List<String>> customRulesByJavaType = new HashMap<String, List<String>>();

    public CodegenConfig() {
        this.setJavaType("integer", Integer.class.getName(), IntAliasColumn.class.getName());
        this.setJavaType("character", String.class.getName(), StringAliasColumn.class.getName());
        this.setJavaType("character varying", String.class.getName(), StringAliasColumn.class.getName());
        this.setJavaType("text", String.class.getName(), StringAliasColumn.class.getName());
        this.setJavaType("smallint", Short.class.getName(), ShortAliasColumn.class.getName());
        this.setJavaType("bigint", Long.class.getName(), LongAliasColumn.class.getName());
        this.setJavaType("boolean", Boolean.class.getName(), BooleanAliasColumn.class.getName());
        this.setJavaType("bytea", "byte[]", ByteArrayAliasColumn.class.getName());
        this.setJavaType("date", "com.domainlanguage.time.CalendarDate", "joist.domain.orm.queries.columns.CalendarDateAliasColumn");
        this.setJavaType("timestamp without time zone", "com.domainlanguage.time.TimePoint", "joist.domain.orm.queries.columns.TimePointAliasColumn");

        this.setJavaType("int", Integer.class.getName(), IntAliasColumn.class.getName());
        this.setJavaType("bit", Boolean.class.getName(), BooleanAliasColumn.class.getName());
        this.setJavaType("varchar", String.class.getName(), StringAliasColumn.class.getName());
        this.setJavaType("tinyint", Short.class.getName(), ShortAliasColumn.class.getName());
    }

    public CodegenConfig doNotUseTimeAndMoney() {
        this.setJavaType("date", Date.class.getName(), DateAliasColumn.class.getName());
        this.setJavaType("timestamp without time zone", Date.class.getName(), DateAliasColumn.class.getName());
        return this;
    }

    public void setProjectNameForDefaults(String projectName) {
        this.domainObjectPackage = projectName + ".domain";
        this.queriesPackage = projectName + ".domain.queries";
    }

    public void setJavaType(String jdbcDataType, String javaType, String aliasColumnType) {
        this.javaTypeByDataType.put(jdbcDataType, javaType);
        this.aliasTypeByDataType.put(jdbcDataType, aliasColumnType);
    }

    public void setJavaType(String tableName, String columnName, String javaType, String aliasColumnType) {
        this.javaTypeByColumnName.put(tableName + "." + columnName, javaType);
        this.aliasTypeByColumnName.put(tableName + "." + columnName, aliasColumnType);
    }

    public String getJavaType(String tableName, String columnName, String dataType) {
        if (this.javaTypeByColumnName.containsKey(tableName + "." + columnName)) {
            return this.javaTypeByColumnName.get(tableName + "." + columnName);
        }
        if (this.javaTypeByDataType.containsKey(dataType)) {
            return this.javaTypeByDataType.get(dataType);
        }
        throw new RuntimeException("Unmatched data type: " + dataType);
    }

    public String getAliasType(String tableName, String columnName, String dataType) {
        if ("id".equals(columnName)) {
            return IdAliasColumn.class.getName();
        }
        if (this.aliasTypeByColumnName.containsKey(tableName + "." + columnName)) {
            return this.aliasTypeByColumnName.get(tableName + "." + columnName);
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

    public String getDomainObjectPackage() {
        return this.domainObjectPackage;
    }

    public String getQueriesPackage() {
        return this.queriesPackage;
    }

    public String getDomainObjectBaseClass() {
        return this.domainObjectBaseClass;
    }

    public String getQueriesBaseClass() {
        return this.queriesBaseClass;
    }

    public String getOutputSourceDirectory() {
        return this.outputSourceDirectory;
    }

    public String getOutputCodegenDirectory() {
        return this.outputCodegenDirectory;
    }

}
