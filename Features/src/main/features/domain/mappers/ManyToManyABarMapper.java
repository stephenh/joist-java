package features.domain.mappers;

import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.queries.Select;

import features.domain.ManyToManyABar;

public class ManyToManyABarMapper {

    public ManyToManyABar find(Id<ManyToManyABar> id) {
        ManyToManyABarAlias a = new ManyToManyABarAlias("a");
        return Select.from(a).where(a.id.equals(id)).unique();
    }

}
