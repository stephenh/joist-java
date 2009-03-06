package org.exigencecorp.conversion;

import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.exigencecorp.util.Copy;

public class DefaultConverters {

    public static Converter<Object, String> objectToString = new AbstractOneWayConverter<Object, String>() {
        public String convertOneToTwo(Object value, Class<? extends String> toType) {
            return ObjectUtils.toString(value);
        }
    };

    public static Converter<String, Integer> stringToInteger = new AbstractOneWayConverter<String, Integer>() {
        public Integer convertOneToTwo(String value, Class<? extends Integer> toType) {
            return new Integer(value);
        }

    };

    public static List<Converter<?, ?>> all = Copy.list(new Converter<?, ?>[] { //
        DefaultConverters.objectToString, DefaultConverters.stringToInteger });

}
