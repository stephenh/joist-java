package features.domain.queries;

import java.util.List;

import joist.domain.orm.queries.Select;
import joist.domain.orm.queries.Update;
import features.domain.Primitives;
import features.domain.PrimitivesAlias;

public class PrimitivesQueries extends PrimitivesQueriesCodegen {

  public Primitives findByName(String name) {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.name.eq(name));
    return q.unique();
  }

  public List<Integer> findIdsWithNameLike(String name) {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.name.like(name));
    return q.listIds();
  }

  public long countWhereFlagIs(boolean flag) {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.flag.eq(flag));
    return q.count();
  }

  public Primitives find(int id) {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.id.eq(id));
    return q.unique();
  }

  public void setFlag(List<Integer> ids, boolean value) {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Update<Primitives> u = Update.into(p);
    u.set(p.flag.to(true));
    u.where(p.id.in(ids));
    u.execute();
  }

  public String findNameOnly(int id) {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.select(p.name.as("name"));
    q.where(p.id.eq(id));
    return q.unique(String.class);
  }

  public List<NameAndFlag> findNameAndFlagOnly() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.select(p.name.as("name"), p.flag.as("flag"));
    q.orderBy(p.id.asc());
    return q.list(NameAndFlag.class);
  }

  public static class NameAndFlag {
    public String name;
    public Boolean flag;
  }

}
