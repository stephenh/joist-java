package joist.domain.util;


import joist.domain.DomainObject;
import joist.domain.uow.UoW;

import org.exigencecorp.conversion.AbstractConverter;

public class DomainObjectToStringConverter extends AbstractConverter<DomainObject, String> {

    public String convertOneToTwo(DomainObject value, Class<? extends String> toType) {
        return value.getId() == null ? "" : value.getId().toString();
    }

    public DomainObject convertTwoToOne(String value, Class<? extends DomainObject> toType) {
        return UoW.load(toType, new Integer(value));
    }

}
