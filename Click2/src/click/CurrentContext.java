package click;

public class CurrentContext {

    private static final ThreadLocal<ClickContext> CURRENT = new ThreadLocal();

    public static ClickContext get() {
        return CurrentContext.CURRENT.get();
    }

    public static void set(ClickContext context) {
        CurrentContext.CURRENT.set(context);
    }

    public static void addControlToCurrentPage(Control control) {
        CurrentContext.get().getPage().addControl(control);
    }

}
