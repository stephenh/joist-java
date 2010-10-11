package joist.domain.orm.queries;

import java.util.ArrayList;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;
import joist.util.StringBuilderr;
import joist.util.Wrap;

/** A class for issuing UPDATEs against a table with custom SETs and WHERE clauses. */
public class Update<T extends DomainObject> {

  private final Alias<T> alias;
  private final List<SetItem<T>> setItems = new ArrayList<SetItem<T>>();
  private Where where = null;

  public static <T extends DomainObject> Update<T> into(Alias<T> alias) {
    return new Update<T>(alias);
  }

  private Update(Alias<T> alias) {
    this.alias = alias;
  }

  public void set(SetItem<T> setItem) {
    this.setItems.add(setItem);
  }

  public void where(Where where) {
    this.where = where;
  }

  public int execute() {
    return Jdbc.update(UoW.getConnection(), this.toSql(), this.getParameters());
  }

  public String toSql() {
    StringBuilderr s = new StringBuilderr();
    s.line("UPDATE {}", Wrap.quotes(this.alias.getTableName()));
    s.append(" SET ");
    for (SetItem<T> c : this.setItems) {
      s.append(Wrap.quotes(c.getColumn().getName()));
      s.append(" = ?, ");
    }
    s.stripLastCommaSpace();
    s.line();
    if (this.where != null) {
      s.line(" WHERE {}", this.where.getSqlWithoutAliasPrefix(this.alias.getName()));
    }
    s.stripTrailingNewLine();
    return s.toString();
  }

  public List<Object> getParameters() {
    List<Object> parameters = new ArrayList<Object>();
    for (SetItem<T> item : this.setItems) {
      parameters.add(item.getValue());
    }
    parameters.addAll(this.where.getParameters());
    return parameters;
  }

}
