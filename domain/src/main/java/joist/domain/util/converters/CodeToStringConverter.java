package joist.domain.util.converters;

import joist.converter.AbstractConverter;
import joist.domain.Code;

public class CodeToStringConverter extends AbstractConverter<Code, String> {

  public String convertOneToTwo(Code value, Class<? extends String> toType) {
    return value.toString();
  }

  @SuppressWarnings("rawtypes")
  public Code convertTwoToOne(String value, Class<? extends Code> toType) {
    // javac needs the double cast--ugh
    return (Code) Enum.valueOf((Class<Enum>) (Class<?>) toType, value);
  }
}
