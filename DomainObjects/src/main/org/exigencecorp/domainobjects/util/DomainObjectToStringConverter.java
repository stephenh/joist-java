package org.exigencecorp.domainobjects.util;

import org.exigencecorp.conversion.AbstractConverter;
import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.uow.UoW;

public class DomainObjectToStringConverter extends AbstractConverter<DomainObject, String> {

    public String convertOneToTwo(DomainObject value, Class<? extends String> toType) {
        return value.getId() == null ? "" : value.getId().toString();
    }

    public DomainObject convertTwoToOne(String value, Class<? extends DomainObject> toType) {
        return UoW.load(toType, new Integer(value));
    }

}
