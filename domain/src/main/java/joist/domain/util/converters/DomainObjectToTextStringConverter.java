package joist.domain.util.converters;

import joist.converter.AbstractOneWayConverter;
import joist.domain.DomainObject;

public class DomainObjectToTextStringConverter extends AbstractOneWayConverter<DomainObject, String> {

  public String convertOneToTwo(DomainObject value, Class<? extends String> toType) {
    return value.toTextString();
  }

}
