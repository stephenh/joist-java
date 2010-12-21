package joist.codegen;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
  public String outputSourceDirectory = "./src/main/java";

  /** Where the re-generated base classes (e.g. EmployeeCodegen) that you do not edit go. @return E.g. <code>src/codegen</code> */
  public String outputCodegenDirectory = "./src/codegen";

  /** The package name of your domain objects. @return E.g. <code>app.domain</code> */
  public String domainObjectPackage = "project.domain";

  /** The package name of your query objects. @return E.g. <code>app.domain.queries</code> */
  public String queriesPackage = "project.domain.queries";

  /** The package name of your builder objects. @return E.g. <code>app.domain.builders</code> */
  public String buildersPackage = "project.domain.builders";

  /** The base class for all the generated base classes (e.g. EmployeeCodegen). @return E.g. <code>YourAbstractDomainObject</code> */
  public String domainObjectBaseClass = AbstractDomainObject.class.getName();

  /** The base class for the once-touched queries objects. */
  public String queriesBaseClass = AbstractQueries.class.getName() + "<{}>";

  // Private structures
  private final Map<String, String> javaTypeByDataType = new HashMap<String, String>();
  private final Map<String, String> javaTypeByColumnName = new HashMap<String, String>();
  private final Map<TypeAndPattern, String> javaTypeByPattern = new HashMap<TypeAndPattern, String>();
  private final Map<String, String> aliasTypeByDataType = new HashMap<String, String>();
  private final Map<String, String> aliasTypeByColumnName = new HashMap<String, String>();
  private final Map<TypeAndPattern, String> aliasTypeByPattern = new HashMap<TypeAndPattern, String>();
  private final Map<String, String> getterAccessByTableAndColumn = new HashMap<String, String>();
  private final Map<String, String> setterAccessByTableAndColumn = new HashMap<String, String>();
  private final List<String> doNotIncrementParentsOpLock = new ArrayList<String>();
  private final List<String> skipCollection = new ArrayList<String>();
  private final List<String> skipTable = new ArrayList<String>();
  private final List<String> notAbstractEvenThoughSubclassed = new ArrayList<String>();
  private final Map<String, List<String>> customRulesByJavaType = new HashMap<String, List<String>>();
  private final String amountSuffix = ".*amount$";

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
    this.setJavaType("timestamp", "com.domainlanguage.time.TimePoint", "joist.domain.orm.queries.columns.TimePointAliasColumn"); // mysql
    this.setJavaTypePattern("integer", this.amountSuffix, "com.domainlanguage.money.Money", "joist.domain.orm.queries.columns.MoneyAliasColumn");
    this.setJavaTypePattern("bigint", this.amountSuffix, "com.domainlanguage.money.Money", "joist.domain.orm.queries.columns.MoneyAliasColumn");

    this.setJavaType("int", Integer.class.getName(), IntAliasColumn.class.getName());
    this.setJavaType("bit", Boolean.class.getName(), BooleanAliasColumn.class.getName());
    this.setJavaType("varchar", String.class.getName(), StringAliasColumn.class.getName());
    this.setJavaType("tinyint", Short.class.getName(), ShortAliasColumn.class.getName());
  }

  public CodegenConfig doNotUseTimeAndMoney() {
    this.setJavaType("date", Date.class.getName(), DateAliasColumn.class.getName());
    this.setJavaType("timestamp without time zone", Date.class.getName(), DateAliasColumn.class.getName());
    this.javaTypeByPattern.remove(new TypeAndPattern("integer", this.amountSuffix));
    this.javaTypeByPattern.remove(new TypeAndPattern("bigint", this.amountSuffix));
    this.aliasTypeByPattern.remove(new TypeAndPattern("integer", this.amountSuffix));
    this.aliasTypeByPattern.remove(new TypeAndPattern("bigint", this.amountSuffix));
    return this;
  }

  public void setProjectNameForDefaults(String projectName) {
    this.domainObjectPackage = projectName + ".domain";
    this.queriesPackage = projectName + ".domain.queries";
    this.buildersPackage = projectName + ".domain.builders";
  }

  public void setJavaType(String jdbcDataType, String javaType, String aliasColumnType) {
    this.javaTypeByDataType.put(jdbcDataType, javaType);
    this.aliasTypeByDataType.put(jdbcDataType, aliasColumnType);
  }

  public void setJavaType(String tableName, String columnName, String javaType, String aliasColumnType) {
    this.javaTypeByColumnName.put(tableName + "." + columnName, javaType);
    this.aliasTypeByColumnName.put(tableName + "." + columnName, aliasColumnType);
  }

  public void setJavaTypePattern(String jdbcType, String columnNameRegex, String javaType, String aliasColumnType) {
    this.javaTypeByPattern.put(new TypeAndPattern(jdbcType, columnNameRegex), javaType);
    this.aliasTypeByPattern.put(new TypeAndPattern(jdbcType, columnNameRegex), aliasColumnType);
  }

  public String getJavaType(String tableName, String columnName, String dataType) {
    if ("id".equals(columnName) || "version".equals(columnName)) {
      return "Long";
    }
    if (this.javaTypeByColumnName.containsKey(tableName + "." + columnName)) {
      return this.javaTypeByColumnName.get(tableName + "." + columnName);
    }
    for (Map.Entry<TypeAndPattern, String> e : this.javaTypeByPattern.entrySet()) {
      if (e.getKey().matches(dataType, columnName)) {
        return e.getValue();
      }
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
    if ("version".equals(columnName)) {
      return LongAliasColumn.class.getName();
    }
    if (this.aliasTypeByColumnName.containsKey(tableName + "." + columnName)) {
      return this.aliasTypeByColumnName.get(tableName + "." + columnName);
    }
    for (Map.Entry<TypeAndPattern, String> e : this.aliasTypeByPattern.entrySet()) {
      if (e.getKey().matches(dataType, columnName)) {
        return e.getValue();
      }
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

  public void setTableSkipped(String tableName) {
    this.skipTable.add(tableName);
  }

  public boolean isTableSkipped(String tableName) {
    return this.skipTable.contains(tableName);
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

  public String getBuildersPackage() {
    return this.buildersPackage;
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

  private class TypeAndPattern {
    private final String type;
    private final String regex;
    private final Pattern pattern;

    private TypeAndPattern(String type, String pattern) {
      this.type = type;
      this.regex = pattern;
      this.pattern = Pattern.compile(pattern);
    }

    private boolean matches(String dataType, String columnName) {
      return this.type.equals(dataType) && this.pattern.matcher(columnName).matches();
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof TypeAndPattern) {
        TypeAndPattern other = (TypeAndPattern) o;
        return other.type.equals(this.type) && other.regex.equals(this.regex);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return this.type.hashCode() + this.regex.hashCode();
    }
  }

}
