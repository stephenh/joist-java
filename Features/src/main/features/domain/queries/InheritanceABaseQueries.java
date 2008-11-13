package features.domain.queries;

import java.util.List;

import org.exigencecorp.domainobjects.AbstractQueries;
import org.exigencecorp.domainobjects.queries.Select;

import features.domain.InheritanceABase;
import features.domain.InheritanceABaseAlias;

public class InheritanceABaseQueries extends AbstractQueries<InheritanceABase> {

    public InheritanceABaseQueries() {
        super(InheritanceABase.class);
    }

    public List<InheritanceABase> findAll() {
        InheritanceABaseAlias b = new InheritanceABaseAlias("b");
        return Select.from(b).orderBy(b.id.asc()).list();
    }

}
