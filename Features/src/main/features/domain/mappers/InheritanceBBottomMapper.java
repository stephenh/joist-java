package features.domain.mappers;

import org.exigencecorp.domainobjects.queries.Select;

import features.domain.InheritanceBBottom;

public class InheritanceBBottomMapper {

    public InheritanceBBottom find(Integer id) {
        InheritanceBBottomAlias b = new InheritanceBBottomAlias("b");
        return Select.from(b).where(b.id.equals(id)).unique();
    }

}
