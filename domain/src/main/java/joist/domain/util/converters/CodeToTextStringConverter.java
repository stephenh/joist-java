package joist.domain.util.converters;

import joist.converter.AbstractOneWayConverter;
import joist.domain.Code;

public class CodeToTextStringConverter extends AbstractOneWayConverter<Code, String> {

  public String convertOneToTwo(Code value, Class<? extends String> toType) {
    return value.getName();
  }

}
