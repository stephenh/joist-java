package joist.domain.util.converters;

import joist.converter.AbstractConverter;
import joist.domain.DomainObject;
import joist.domain.uow.UoW;

public class DomainObjectToStringConverter extends AbstractConverter<DomainObject, String> {

  public String convertOneToTwo(DomainObject value, Class<? extends String> toType) {
    return value.getId() == null ? "" : value.getId().toString();
  }

  public DomainObject convertTwoToOne(String value, Class<? extends DomainObject> toType) {
    if ("".equals(value)) {
      return null;
    }
    return UoW.load(toType, new Long(value));
  }

}
