package org.exigencecorp.domainobjects.codegen.dtos;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.CodegenConfig;
import org.exigencecorp.domainobjects.codegen.InformationSchemaColumn;
import org.exigencecorp.util.Inflector;

public class ManyToManyProperty implements Property {

    private CodegenConfig config;
    private String myKeyColumnName;
    private Entity joinTable;
    private Entity mySide;
    private Entity targetTable;
    private boolean inverse;
    private ManyToManyProperty other;

    public ManyToManyProperty(Codegen codegen, Entity joinTable, Entity mySide, Entity otherSide, InformationSchemaColumn column) {
        this.config = codegen.getConfig();
        this.joinTable = joinTable;
        this.mySide = mySide;
        this.targetTable = otherSide;
        this.myKeyColumnName = column.name;
        this.inverse = column.tableName.startsWith(mySide.getTableName());
    }

    public String getCapitalVariableNameSingular() {
        // rolled_id -> Rolled
        return Inflector.camelize(this.getOtherKeyColumnName().replaceAll("_id", ""));
    }

    public String getCapitalVariableName() {
        return this.getCapitalVariableNameSingular() + "s";
    }

    public String getJavaType() {
        return "List<" + this.getTargetJavaType() + ">";
    }

    public String getDefaultJavaString() {
        return "new ArrayList<" + this.getTargetJavaType() + ">()";
    }

    public String getTargetJavaType() {
        return this.targetTable.getClassName();
    }

    public List<String> getCustomRules() {
        return this.config.getCustomRules(this.mySide.getClassName(), this.getJavaType(), this.getVariableName());
    }

    public String getVariableName() {
        return StringUtils.uncapitalize(this.getCapitalVariableName());
    }

    /** Doesn't really apply to us as our column is defined in another table. */
    public boolean isNotNull() {
        return false;
    }

    /** Doesn't really apply to us as our column is defined in another table. */
    public int getMaxCharacterLength() {
        return 0;
    }

    public String getJoinTableName() {
        return this.joinTable.getTableName();
    }

    public String getMyKeyColumnName() {
        return this.myKeyColumnName;
    }

    public String getTargetKeyColumnName() {
        return this.targetTable.getTableName() + "_id";
    }

    public Entity getTargetTable() {
        return this.targetTable;
    }

    public String getTargetTableName() {
        return this.targetTable.getTableName();
    }

    public String getSequenceName() {
        return this.getJoinTableName() + "_id_seq";
    }

    public boolean isInverse() {
        return this.inverse;
    }

    public String getSortBy() {
        return this.targetTable.getSortBy();
    }

    public ManyToManyProperty getOther() {
        return this.other;
    }

    public void setOther(ManyToManyProperty other) {
        this.other = other;
    }

    public String getOtherKeyColumnName() {
        return this.other.getMyKeyColumnName();
    }

    public boolean isNotGenerated() {
        return false;
    }

    public boolean getNoTicking() {
        return this.config.isDoNotIncrementParentsOpLock(this.mySide.getClassName(), this.getVariableName());
    }

    public boolean isCode() {
        return this.targetTable.isEnum();
    }

    public Entity getJoinTable() {
        return this.joinTable;
    }

}
