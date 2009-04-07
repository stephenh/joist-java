package joist.domain.util;

import joist.converter.AbstractConverter;
import joist.domain.Code;

public class CodeToStringConverter extends AbstractConverter<Code, String> {

    public String convertOneToTwo(Code value, Class<? extends String> toType) {
        return value.toString();
    }

    @SuppressWarnings("unchecked")
    public Code convertTwoToOne(String value, Class<? extends Code> toType) {
        return (Code) Enum.valueOf((Class<Enum>) toType, value);

    }
}
