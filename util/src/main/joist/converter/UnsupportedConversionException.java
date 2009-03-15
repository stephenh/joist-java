package joist.converter;

public class UnsupportedConversionException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final Object value;
    private final Class<?> toType;

    public UnsupportedConversionException(Object value, Class<?> toType) {
        this.value = value;
        this.toType = toType;
    }

    public String getMessage() {
        return "Could not covert " + this.value + " to " + this.toType;
    }

    public Object getValue() {
        return this.value;
    }

    public Class<?> getToType() {
        return this.toType;
    }

}
