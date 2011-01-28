package joist.codegen.dtos;

import java.util.List;

import joist.codegen.CodegenConfig;
import joist.codegen.InformationSchemaColumn;
import joist.util.Inflector;

import org.apache.commons.lang.StringUtils;

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
  private Entity manySide;
  private String constraintName;
  private String columnName;
  private OneToManyProperty oneToManyProperty;
  private boolean isNotNull;

  public ManyToOneProperty(Entity manySide, InformationSchemaColumn column) {
    this.config = manySide.config;
    this.manySide = manySide;
    this.columnName = column.name;
    this.constraintName = column.foreignKeyConstraintName;
    this.isNotNull = !column.nullable;
    this.assertValidConstraintName();
  }

  public Entity getOneSide() {
    return this.oneToManyProperty.getOneSide();
  }

  public Entity getManySide() {
    return this.manySide;
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
    return this.getOneSide().getClassName();
  }

  public List<String> getCustomRules() {
    return this.config.getCustomRules(this.getManySide().getClassName(), this.getJavaType(), this.getVariableName());
  }

  public boolean isOwnerMe() {
    return this.constraintName.contains("_isme");
  }

  public boolean isOwnerThem() {
    return this.constraintName.contains("_isthem");
  }

  public boolean isOwnerNeither() {
    return !this.isOwnerMe() && !this.isOwnerThem();
  }

  private void assertValidConstraintName() {
    boolean ownerOkay = this.constraintName.contains("_isme")
      || this.constraintName.contains("_isthem")
      || this.constraintName.contains("_isneither");
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
    return this.config.getGetterAccess(this.manySide.getTableName(), this.columnName);
  }

  public String getSetterAccessLevel() {
    return this.config.getSetterAccess(this.manySide.getTableName(), this.columnName);
  }

  public boolean isNotNull() {
    return this.isNotNull;
  }

}
