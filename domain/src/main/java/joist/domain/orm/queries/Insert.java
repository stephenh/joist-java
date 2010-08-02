package joist.domain.orm.queries;

import java.util.ArrayList;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;
import joist.util.StringBuilderr;
import joist.util.Wrap;

import org.apache.commons.lang.StringUtils;

public class Insert<T extends DomainObject> {

  public static <T extends DomainObject> Insert<T> into(Alias<T> alias) {
    return new Insert<T>(alias);
  }

  private final Alias<T> alias;
  private final List<AliasColumn<T, ?, ?>> columns = new ArrayList<AliasColumn<T, ?, ?>>();
  private final List<Object> parameters = new ArrayList<Object>();

  private Insert(Alias<T> alias) {
    this.alias = alias;
  }

  public void set(SetItem<T> setItem) {
    this.columns.add(setItem.getColumn());
    this.parameters.add(setItem.getValue());
  }

  public int execute() {
    return Jdbc.update(UoW.getConnection(), this.toSql(), this.parameters);
  }

  public String toSql() {
    StringBuilderr s = new StringBuilderr();
    s.append("INSERT INTO ");
    s.append(Wrap.quotes(this.alias.getTableName()));
    s.append(" (");
    for (AliasColumn<T, ?, ?> c : this.columns) {
      s.append(Wrap.quotes(c.getName()));
      s.append(", ");
    }
    s.stripLastCommaSpace();
    s.append(")");
    s.append(" VALUES (");
    s.append(StringUtils.repeat("?, ", this.columns.size()));
    s.stripLastCommaSpace();
    s.append(")");
    return s.toString();
  }

}
