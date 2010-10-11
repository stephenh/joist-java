package joist.domain.orm.queries;

import java.util.ArrayList;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;
import joist.util.StringBuilderr;
import joist.util.Wrap;

public class Delete<T extends DomainObject> {

  public static <T extends DomainObject> Delete<T> from(Alias<T> alias) {
    return new Delete<T>(alias);
  }

  private final Alias<T> alias;
  private final List<Object> parameters = new ArrayList<Object>();
  private Where where = null;

  private Delete(Alias<T> alias) {
    this.alias = alias;
  }

  public int execute() {
    return Jdbc.update(UoW.getConnection(), this.toSql(), this.parameters);
  }

  public Delete<T> where(Where where) {
    if (this.where != null) {
      throw new RuntimeException("Already set");
    }
    this.where = where;
    this.parameters.addAll(where.getParameters());
    return this;
  }

  public String toSql() {
    StringBuilderr s = new StringBuilderr();
    s.append("DELETE FROM ");
    s.line(Wrap.quotes(this.alias.getTableName()));
    if (this.where != null) {
      s.line(" WHERE {}", this.where.getSqlWithoutAliasPrefix(this.alias.getName()));
    }
    s.stripTrailingNewLine();
    return s.toString();
  }

}
