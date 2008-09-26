package click.util;

public class Cast {

    private Cast() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T orNull(Class<T> type, Object instance) {
        if (type.isInstance(instance)) {
            return (T) instance;
        } else {
            return null;
        }
    }

}
