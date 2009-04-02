package joist.converter;

import java.util.List;

import joist.util.Copy;

public class DefaultConverters {

    public static Converter<Object, String> objectToString = new AbstractOneWayConverter<Object, String>() {
        public String convertOneToTwo(Object value, Class<? extends String> toType) {
            return value == null ? null : value.toString();
        }
    };

    public static Converter<String, Integer> stringToInteger = new AbstractOneWayConverter<String, Integer>() {
        public Integer convertOneToTwo(String value, Class<? extends Integer> toType) {
            return new Integer(value);
        }
    };

    // This is kind of different--instead of being a simple converter, this tries to just handle all of the
    // default Boolean cases. Makes for less converters that are more complex. We'll see how this goes.
    public static Converter<Boolean, Object> booleanToObject = new AbstractConverter<Boolean, Object>() {
        public Object convertOneToTwo(Boolean value, Class<? extends Object> toType) {
            if (String.class == toType) {
                return value.toString();
            } else {
                throw new UnsupportedConversionException(value, toType);
            }
        }

        public Boolean convertTwoToOne(Object value, Class<? extends Boolean> toType) {
            if (value instanceof String) {
                return Boolean.valueOf((String) value);
            } else {
                return Boolean.FALSE;
            }
        }
    };

    public static List<Converter<?, ?>> all = Copy.list(new Converter<?, ?>[] { //
        DefaultConverters.objectToString, DefaultConverters.stringToInteger, DefaultConverters.booleanToObject });

}
