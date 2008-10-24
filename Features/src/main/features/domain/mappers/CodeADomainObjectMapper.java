package features.domain.mappers;

import org.exigencecorp.domainobjects.queries.Select;

import features.domain.CodeADomainObject;

public class CodeADomainObjectMapper {

    public CodeADomainObject find(int id) {
        CodeADomainObjectAlias p = new CodeADomainObjectAlias("p");
        Select<CodeADomainObject> q = Select.from(p);
        q.where(p.id.equals(id));
        return q.unique();
    }

}
