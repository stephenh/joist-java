package org.exigencecorp.domainobjects.util;

import org.exigencecorp.conversion.AbstractOneWayConverter;
import org.exigencecorp.domainobjects.DomainObject;

public class DomainObjectToTextStringConverter extends AbstractOneWayConverter<DomainObject, String> {

    public String convertOneToTwo(DomainObject value, Class<? extends String> toType) {
        return value.toTextString();
    }

}
