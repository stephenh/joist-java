package joist.migrations.columns;

public class VarcharColumn extends AbstractColumn<VarcharColumn> {

  private int length;

  public VarcharColumn(String name) {
    super(name, "varchar");
    this.length = 100;
  }

  public VarcharColumn length(int length) {
    this.length = length;
    return this;
  }

  @Override
  public String getDataType() {
    return super.getDataType() + "(" + this.length + ")";
  }

}
