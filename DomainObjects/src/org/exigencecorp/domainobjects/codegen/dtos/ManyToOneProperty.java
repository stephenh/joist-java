package org.exigencecorp.domainobjects.codegen.dtos;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.codegen.CodegenConfig;
import org.exigencecorp.domainobjects.codegen.InformationSchemaColumn;
import org.exigencecorp.util.Inflector;

/**
 * For the child.parent_id column in a parent/column relationship.
 *
 * <code>
 * parent                   child
 *   id                   parent_id
 *    \--> one to many --> /
 *     <-- one to many <--/
 * </code>
 */
public class ManyToOneProperty implements Property {

    private CodegenConfig config;
    private Entity oneSide;
    private String constraintName;
    private String columnName;
    private boolean isNotNull;
    private OneToManyProperty oneToManyProperty;

    public ManyToOneProperty(Entity oneSide, InformationSchemaColumn column) {
        this.config = oneSide.config;
        this.oneSide = oneSide;
        this.columnName = column.name;
        this.constraintName = column.foreignKeyConstraintName;
        this.isNotNull = !column.nullable;
        this.assertValidConstraintName();
    }

    public Entity getManySide() {
        return this.oneToManyProperty.getManySide();
    }

    public Entity getOneSide() {
        return this.oneSide;
    }

    public String getCapitalVariableName() {
        return Inflector.camelize(this.getColumnName().replaceAll("_id$", ""));
    }

    public String getColumnName() {
        return this.columnName;
    }

    public String getVariableName() {
        return StringUtils.uncapitalize(this.getCapitalVariableName());
    }

    public String getTheOtherSide() {
        String overrideName = this.config.getForeignKeyProperty(this.getManySide().getTableName(), this.columnName);
        if (overrideName != null) {
            // Boundary case, we have an explicit override
            return StringUtils.capitalize(overrideName) + this.getJavaType();
        } else if (this.getCapitalVariableName().equals(this.getManySide().getClassName())) {
            // Default case, return our class name
            return this.oneSide.getClassName();
        } else {
            // Boundary case, non-class name column name, so use column name + class name
            return this.getCapitalVariableName() + this.oneSide.getClassName();
        }
    }

    public String getJavaType() {
        return this.getManySide().getClassName();
    }

    public List<String> getCustomRules() {
        return this.config.getCustomRules(this.getOneSide().getClassName(), this.getJavaType(), this.getVariableName());
    }

    public String getDefaultJavaString() {
        return "null";
    }

    public boolean isOwnerMe() {
        return this.constraintName.contains("owner_isme");
    }

    public boolean isOwnerThem() {
        return this.constraintName.contains("owner_isthem");
    }

    public boolean isOwnerNeither() {
        return !this.isOwnerMe() && !this.isOwnerThem();
    }

    /** See http://www.jroller.com/page/dgolla?entry=hibernate--kinda of, doing lazy=false meant defuckProxy isn't needed anymore. */
    public boolean isNotLazy() {
        return this.getManySide().hasSubclasses();
    }

    public boolean isToEnum() {
        return this.getManySide().isEnum();
    }

    public boolean isTargetGenerated() {
        return !this.getOneToManyProperty().isNotGenerated();
    }

    public boolean isNotNull() {
        return this.isNotNull;
    }

    /** Doesn't really deal with us as we're not varchars. */
    public int getMaxCharacterLength() {
        return 0;
    }

    private void assertValidConstraintName() {
        boolean ownerOkay = this.constraintName.contains("owner_isme")
            || this.constraintName.contains("owner_isthem")
            || this.constraintName.contains("owner_isneither");
        boolean suffixOkay = this.constraintName.endsWith("_fk");
        if (!ownerOkay || !suffixOkay) {
            throw new RuntimeException("Invalid constraint name " + this.constraintName);
        }
    }

    public boolean isNotGenerated() {
        return false;
    }

    public OneToManyProperty getOneToManyProperty() {
        return this.oneToManyProperty;
    }

    public void setOneToManyProperty(OneToManyProperty incomingForeignKeyProperty) {
        this.oneToManyProperty = incomingForeignKeyProperty;
    }

    public String getGetterAccessLevel() {
        return this.config.getGetterAccess(this.oneSide.getTableName(), this.columnName);
    }

    public String getSetterAccessLevel() {
        return this.config.getSetterAccess(this.oneSide.getTableName(), this.columnName);
    }

    public boolean isNotPercolated() {
        // Hack
        return "claim.claim_source_id".equals(this.oneSide.getTableName() + "." + this.columnName);
    }

    public boolean isCode() {
        return this.getOneSide().isEnum();
    }

}
