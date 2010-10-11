package joist.migrations.columns;

public class CharColumn extends AbstractColumn<CharColumn> {

  private Integer length;

  public CharColumn(String name, Integer length) {
    super(name, "char");
    this.length = length;
  }

  public String toSql() {
    if (this.length != null) {
      return this.getQuotedName() + " char(" + this.length + ")";
    } else {
      return super.toSql();
    }
  }

}
