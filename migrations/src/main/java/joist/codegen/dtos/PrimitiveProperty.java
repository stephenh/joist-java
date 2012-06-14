package joist.codegen.dtos;

import java.lang.reflect.Method;
import java.util.List;

import joist.codegen.Codegen;
import joist.codegen.Config;
import joist.codegen.InformationSchemaColumn;
import joist.util.Inflector;

import org.apache.commons.lang.StringUtils;

public class PrimitiveProperty {

  private final Config config;
  private final Entity entity;
  private final String columnName;
  private final String dataType;
  private final boolean isNotNull;
  private final int maxCharacterLength;
  private final String defaultValue;
  private final boolean unique;

  public PrimitiveProperty(Codegen codegen, Entity entity, InformationSchemaColumn column) {
    this.config = codegen.getConfig();
    this.entity = entity;
    this.columnName = column.name;
    this.dataType = column.dataType;
    this.isNotNull = !column.nullable;
    this.defaultValue = column.defaultValue;
    this.maxCharacterLength = column.maximumLength;
    this.unique = column.unique;
  }

  public String getAliasColumnClassName() {
    return this.config.getAliasType(this.entity.getTableName(), this.columnName, this.dataType);
  }

  public String getCapitalVariableName() {
    return Inflector.camelize(this.getColumnName());
  }

  public String getVariableName() {
    return StringUtils.uncapitalize(this.getCapitalVariableName());
  }

  public String getJavaType() {
    return this.config.getJavaType(this.entity.getTableName(), this.getColumnName(), this.dataType);
  }

  public String getDefaultJavaString() {
    if (this.defaultValue != null) {
      try {
        Class<?> aliasColumn = Class.forName(this.getAliasColumnClassName());
        Method defaultValueMethod = aliasColumn.getMethod("defaultValue", String.class);
        return (String) defaultValueMethod.invoke(null, this.defaultValue);
      } catch (Exception e) {
        // ignore
      }
    }
    return "null";
  }

  public List<String> getCustomRules() {
    return this.config.getCustomRules(this.entity.getClassName(), this.getJavaType(), this.getVariableName());
  }

  public String getColumnName() {
    return this.columnName;
  }

  public boolean shouldHaveNotNullRule() {
    return this.isNotNull && !"id".equals(this.columnName) && !"version".equals(this.columnName);
  }

  public int getMaxCharacterLength() {
    return this.maxCharacterLength;
  }

  public String getGetterAccessLevel() {
    return this.config.getGetterAccess(this.entity.getTableName(), this.columnName);
  }

  public String getSetterAccessLevel() {
    return this.config.getSetterAccess(this.entity.getTableName(), this.columnName);
  }

  public boolean isUnique() {
    return this.unique;
  }
}
