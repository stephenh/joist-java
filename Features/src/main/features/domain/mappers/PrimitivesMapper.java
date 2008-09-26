package features.domain.mappers;

import java.util.List;

import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.queries.Update;

import features.domain.Primitives;

public class PrimitivesMapper {

    public Primitives findByName(String name) {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(p.name.equals(name));
        return q.unique();
    }

    public Primitives find(int id) {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(p.id.equals(id));
        return q.unique();
    }

    public void setFlag(List<Integer> ids, boolean value) {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Update<Primitives> u = Update.into(p);
        u.set(p.flag.to(true));
        u.where(p.id.in(ids));
        u.execute();
    }

}
