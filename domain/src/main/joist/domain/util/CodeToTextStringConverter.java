package joist.domain.util;


import joist.domain.Code;

import org.exigencecorp.conversion.AbstractOneWayConverter;

public class CodeToTextStringConverter extends AbstractOneWayConverter<Code, String> {

    public String convertOneToTwo(Code value, Class<? extends String> toType) {
        return value.getName();
    }

}
