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
public class ManyToOneProperty {

    private CodegenConfig config;
    private Entity oneSide;
    private String constraintName;
    private String columnName;
    private OneToManyProperty oneToManyProperty;

    public ManyToOneProperty(Entity oneSide, InformationSchemaColumn column) {
        this.config = oneSide.config;
        this.oneSide = oneSide;
        this.columnName = column.name;
        this.constraintName = column.foreignKeyConstraintName;
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

    public String getJavaType() {
        return this.getManySide().getClassName();
    }

    public List<String> getCustomRules() {
        return this.config.getCustomRules(this.getOneSide().getClassName(), this.getJavaType(), this.getVariableName());
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

    private void assertValidConstraintName() {
        boolean ownerOkay = this.constraintName.contains("owner_isme")
            || this.constraintName.contains("owner_isthem")
            || this.constraintName.contains("owner_isneither");
        if (!ownerOkay) {
            // throw new RuntimeException("Invalid constraint name " + this.constraintName);
        }
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

}
