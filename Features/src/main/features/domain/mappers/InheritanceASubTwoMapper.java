package features.domain.mappers;

import org.exigencecorp.domainobjects.queries.Select;

import features.domain.InheritanceASubTwo;

public class InheritanceASubTwoMapper {

    public InheritanceASubTwo find(int id) {
        InheritanceASubTwoAlias sb = new InheritanceASubTwoAlias("sb");
        Select<InheritanceASubTwo> q = Select.from(sb);
        q.where(sb.id.equals(id));
        return q.unique();
    }

}
