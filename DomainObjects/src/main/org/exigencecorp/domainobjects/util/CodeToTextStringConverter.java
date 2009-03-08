package org.exigencecorp.domainobjects.util;

import org.exigencecorp.conversion.AbstractOneWayConverter;
import org.exigencecorp.domainobjects.Code;

public class CodeToTextStringConverter extends AbstractOneWayConverter<Code, String> {

    public String convertOneToTwo(Code value, Class<? extends String> toType) {
        return value.getName();
    }

}
