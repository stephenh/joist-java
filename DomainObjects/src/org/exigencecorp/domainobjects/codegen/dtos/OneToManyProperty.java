package org.exigencecorp.domainobjects.codegen.dtos;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.codegen.CodegenConfig;
import org.exigencecorp.domainobjects.codegen.InformationSchemaColumn;
import org.exigencecorp.util.Inflector;

/** For entities that are the target of a many-to-one. */
public class OneToManyProperty implements Property {

    private CodegenConfig config;
    private Entity manySide;
    private String constraintName;
    private String keyColumnName;
    private String capitalVariableNameSingular;
    private ManyToOneProperty foreignKeyColumn;

    /** Parent (oneSide) -> child (manySide) */
    public OneToManyProperty(Entity manySide, InformationSchemaColumn oneSideColumn) {
        this.config = manySide.config;
        this.manySide = manySide;
        this.constraintName = oneSideColumn.foreignKeyConstraintName;
        this.keyColumnName = oneSideColumn.name;
    }

    public Entity getOneSide() {
        return this.getForeignKeyColumn().getOneSide();
    }

    public Entity getManySide() {
        return this.manySide;
    }

    public String getCapitalVariableNameSingular() {
        if (this.capitalVariableNameSingular == null) {
            if (this.getKeyPropertyName().equals(this.manySide.getClassName())) {
                // Regular many-to-one relationship of only 1 column in the target table pointing to us, so name our side based on the type
                this.capitalVariableNameSingular = this.getTargetJavaType();
            } else {
                // Boundary case of n columns in the target table pointing to us, so name our side based on their individual column names
                this.capitalVariableNameSingular = this.getKeyPropertyName() + this.getTargetJavaType();
            }
        }
        return this.capitalVariableNameSingular;
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

    public List<String> getCustomRules() {
        return this.config.getCustomRules(this.getOneSide().getClassName(), this.getJavaType(), this.getVariableName());
    }

    public String getKeyPropertyName() {
        return Inflector.camelize(this.keyColumnName.replaceAll("_id$", ""));
    }

    public String getKeyFieldName() {
        String theirPropertyName = Inflector.camelize(this.keyColumnName.replaceAll("_id$", ""));
        return StringUtils.uncapitalize(theirPropertyName);
    }

    public String getVariableName() {
        return StringUtils.uncapitalize(this.getCapitalVariableName());
    }

    public String getTargetJavaType() {
        return this.getOneSide().getClassName();
    }

    public boolean isOwnerMe() {
        return this.constraintName.contains("owner_isthem"); // Since we're incoming, "them" means us
    }

    public boolean isOwnerThem() {
        return this.constraintName.contains("owner_isme"); // Since we're incoming, "me" means them
    }

    public boolean getNoTicking() {
        return this.config.isDoNotIncrementParentsOpLock(this.manySide.getClassName(), this.getVariableName());
    }

    public boolean isCollectionSkipped() {
        return this.config.isCollectionSkipped(this.manySide.getClassName(), this.getVariableName());
    }

    public ManyToOneProperty getForeignKeyColumn() {
        return this.foreignKeyColumn;
    }

    public void setForeignKeyColumn(ManyToOneProperty foreignKeyColumn) {
        this.foreignKeyColumn = foreignKeyColumn;
    }

}
