package joist.domain.codegen.dtos;

import java.util.List;

import joist.domain.codegen.Codegen;
import joist.domain.codegen.CodegenConfig;
import joist.domain.codegen.InformationSchemaColumn;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.util.Inflector;

public class ManyToManyProperty {

    private CodegenConfig config;
    private String myKeyColumnName;
    private Entity joinTable;
    private Entity mySide;
    private Entity targetTable;
    private ManyToManyProperty other;

    public ManyToManyProperty(Codegen codegen, Entity joinTable, Entity mySide, Entity otherSide, InformationSchemaColumn column) {
        this.config = codegen.getConfig();
        this.joinTable = joinTable;
        this.mySide = mySide;
        this.targetTable = otherSide;
        this.myKeyColumnName = column.name;
    }

    public String getCapitalVariableNameSingular() {
        return Inflector.camelize(this.other.myKeyColumnName.replaceAll("_id", "")); // rolled_id -> Rolled
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

    public Entity getTargetTable() {
        return this.targetTable;
    }

    public ManyToManyProperty getOther() {
        return this.other;
    }

    public void setOther(ManyToManyProperty other) {
        this.other = other;
    }

    public ManyToOneProperty getMySideManyToOne() {
        for (ManyToOneProperty mtop : this.joinTable.getManyToOneProperties()) {
            if (mtop.getColumnName().equals(this.myKeyColumnName)) {
                return mtop;
            }
        }
        throw new RuntimeException("No ManyToOne found for " + this.myKeyColumnName);
    }

    public boolean getNoTicking() {
        return this.config.isDoNotIncrementParentsOpLock(this.mySide.getClassName(), this.getVariableName());
    }

    public Entity getJoinTable() {
        return this.joinTable;
    }

}
