package features.domain.mappers;

import org.exigencecorp.domainobjects.queries.Select;

import features.domain.Parent;

public class ParentMapper {

    public ParentMapper() {
    }

    public Parent find(Integer id) {
        ParentAlias p = new ParentAlias("p");
        Select<Parent> q = Select.from(p);
        q.where(p.id.equals(id));
        return q.unique();
    }

}
