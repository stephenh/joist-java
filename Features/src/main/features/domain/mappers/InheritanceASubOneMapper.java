package features.domain.mappers;

import java.util.List;

import org.exigencecorp.domainobjects.queries.Select;

import features.domain.InheritanceASubOne;

public class InheritanceASubOneMapper {

    public InheritanceASubOne find(int id) {
        InheritanceASubOneAlias sa = new InheritanceASubOneAlias("sa");
        Select<InheritanceASubOne> q = Select.from(sa);
        q.where(sa.id.equals(id));
        return q.unique();
    }

    public List<InheritanceASubOne> findAll() {
        InheritanceASubOneAlias sa = new InheritanceASubOneAlias("sa");
        return Select.from(sa).list();
    }

    public InheritanceASubOne findByName(String name) {
        InheritanceASubOneAlias sa = new InheritanceASubOneAlias("sa");
        Select<InheritanceASubOne> q = Select.from(sa);
        q.where(sa.name.equals(name));
        return q.unique();
    }

}
