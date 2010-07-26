package joist.codegen.dtos;

import java.util.List;

import joist.codegen.Codegen;
import joist.codegen.CodegenConfig;
import joist.codegen.InformationSchemaColumn;
import joist.util.Inflector;

import org.apache.commons.lang.StringUtils;

public class PrimitiveProperty {

    private CodegenConfig config;
    private Entity entity;
    private String columnName;
    private String dataType;
    private boolean isNotNull;
    private int maxCharacterLength;
    private String defaultValue;

    public PrimitiveProperty(Codegen codegen, Entity entity, InformationSchemaColumn column) {
        this.config = codegen.getConfig();
        this.entity = entity;
        this.columnName = column.name;
        this.dataType = column.dataType;
        this.isNotNull = !column.nullable;
        this.defaultValue = column.defaultValue;
        this.maxCharacterLength = column.maximumLength;
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
        if (this.dataType.equals("boolean") || this.dataType.equals("bit")) {
            return new Boolean(this.defaultValue).toString();
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

}
