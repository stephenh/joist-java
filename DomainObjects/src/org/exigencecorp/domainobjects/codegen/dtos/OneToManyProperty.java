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
            String overrideName = this.config.getForeignKeyProperty(this.getOneSide().getTableName(), this.keyColumnName);
            if (overrideName != null) {
                // Boudnary case of an explicit override
                this.capitalVariableNameSingular = StringUtils.capitalize(overrideName) + this.getTargetJavaType();
            } else if (Inflector.camelize(this.keyColumnName.replaceAll("_id$", "")).equals(this.manySide.getClassName())) {
                // Regular many-to-one relationship of only 1 column in the target table pointing to us,
                // so name our side based on the type
                this.capitalVariableNameSingular = Inflector.camelize(this.getOneSide().getTableName());
            } else {
                // Boundary case of 2 columns in the target table pointing to us,
                // so name our side based on their individual column name instead of their type
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

    public String getKeyColumnName() {
        return this.keyColumnName;
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
        return Inflector.camelize(this.getOneSide().getTableName());
    }

    public boolean isOwnerMe() {
        // Since we're incoming, "them" means us
        return this.constraintName.contains("owner_isthem");
    }

    public boolean isOwnerThem() {
        // Since we're incoming, "me" means them
        return this.constraintName.contains("owner_isme");
    }

    /** Doesn't really apply to us as our column is defined in another table. */
    public boolean isNotNull() {
        return false;
    }

    /** Doesn't really apply to us, but use it so we can look like IncomingJoinTableProperty */
    public boolean isInverse() {
        return false;
    }

    public boolean getNoTicking() {
        return this.config.isDoNotIncrementParentsOpLock(this.manySide.getClassName(), this.getVariableName());
    }

    public String getSortBy() {
        return this.getOneSide().getSortBy();
    }

    /** Doesn't really apply to us as our column is defined in another table. */
    public int getMaxCharacterLength() {
        return 0;
    }

    public boolean isNotGenerated() {
        return this.config.isCollectionSkipped(this.manySide.getClassName(), this.getVariableName());
    }

    public ManyToOneProperty getForeignKeyColumn() {
        return this.foreignKeyColumn;
    }

    public void setForeignKeyColumn(ManyToOneProperty foreignKeyColumn) {
        this.foreignKeyColumn = foreignKeyColumn;
    }

    public boolean isCode() {
        return this.getOneSide().isCodeEntity();
    }

}
