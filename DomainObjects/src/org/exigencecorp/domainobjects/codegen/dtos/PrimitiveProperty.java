package org.exigencecorp.domainobjects.codegen.dtos;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.CodegenConfig;
import org.exigencecorp.domainobjects.codegen.InformationSchemaColumn;
import org.exigencecorp.util.Inflector;

public class PrimitiveProperty implements Property {

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

    public Class<?> getAliasColumnClass() {
        return this.config.getAliasType(this.entity.getTableName(), this.columnName, this.dataType);
    }

    public String getCapitalVariableName() {
        return Inflector.camelize(this.getColumnName());
    }

    public String getVariableName() {
        return StringUtils.uncapitalize(this.getCapitalVariableName());
    }

    public String getJavaType() {
        if (this.columnName.equals("id")) {
            return "Id<" + this.entity.getClassName() + ">";
        }
        return this.config.getJavaType(this.entity.getTableName(), this.getColumnName(), this.dataType);
    }

    public String getJavaTypeNonPrimitive() {
        String javaType = this.getJavaType();
        if ("boolean".equals(javaType)) {
            return "Boolean";
        }
        return javaType;
    }

    public String getDefaultJavaString() {
        if (this.dataType.equals("boolean")) {
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
