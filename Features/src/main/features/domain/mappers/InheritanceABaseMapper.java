package features.domain.mappers;

import java.util.List;

import org.exigencecorp.domainobjects.queries.Select;

import features.domain.InheritanceABase;

public class InheritanceABaseMapper {

    public InheritanceABase find(int id) {
        InheritanceABaseAlias b = new InheritanceABaseAlias("b");
        Select<InheritanceABase> q = Select.from(b);
        q.where(b.id.equals(id));
        return q.unique();
    }

    public List<InheritanceABase> findAll() {
        InheritanceABaseAlias b = new InheritanceABaseAlias("b");
        return Select.from(b).list();
    }

}
