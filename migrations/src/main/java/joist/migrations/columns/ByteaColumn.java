package joist.migrations.columns;

public class ByteaColumn extends AbstractColumn<ByteaColumn> {

  public ByteaColumn(String name) {
    super(name, "bytea");
  }

}
