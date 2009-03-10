package joist.domain.util;


import joist.domain.DomainObject;

import org.exigencecorp.conversion.AbstractOneWayConverter;

public class DomainObjectToTextStringConverter extends AbstractOneWayConverter<DomainObject, String> {

    public String convertOneToTwo(DomainObject value, Class<? extends String> toType) {
        return value.toTextString();
    }

}
