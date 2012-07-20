package joist.migrations.columns;

public class IntColumn extends AbstractColumn<IntColumn> {

  public IntColumn(String name) {
    super(name, "int");
  }

  public IntColumn defaultValue(int value) {
    return super.defaultValue(Integer.toString(value));
  }

}
