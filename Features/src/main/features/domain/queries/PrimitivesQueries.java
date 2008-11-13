package features.domain.queries;

import java.util.List;

import org.exigencecorp.domainobjects.AbstractQueries;
import org.exigencecorp.domainobjects.Ids;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.queries.Update;

import features.domain.Primitives;
import features.domain.PrimitivesAlias;

public class PrimitivesQueries extends AbstractQueries<Primitives> {

    public PrimitivesQueries() {
        super(Primitives.class);
    }

    public Primitives findByName(String name) {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(p.name.equals(name));
        return q.unique();
    }

    public Ids<Primitives> findIdsWithNameLike(String name) {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(p.name.like(name));
        return q.listIds();
    }

    public long count() {
        return Select.from(new PrimitivesAlias("p")).count();
    }

    public long countWhereFlagIs(boolean flag) {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(p.flag.equals(flag));
        return q.count();
    }

    public Primitives find(int id) {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(p.id.equals(id));
        return q.unique();
    }

    public void setFlag(Ids<Primitives> ids, boolean value) {
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
        q.where(p.id.equals(id));
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
