package features.domain.mappers;

import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.queries.Select;

import features.domain.ManyToManyAFoo;

public class ManyToManyAFooMapper {

    public ManyToManyAFoo find(Id<ManyToManyAFoo> id) {
        ManyToManyAFooAlias f = new ManyToManyAFooAlias("f");
        return Select.from(f).where(f.id.equals(id)).unique();
    }

}
