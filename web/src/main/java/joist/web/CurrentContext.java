package joist.web;

public class CurrentContext {

  private static final ThreadLocal<WebContext> CURRENT = new ThreadLocal<WebContext>();

  public static WebContext get() {
    return CurrentContext.CURRENT.get();
  }

  public static void set(WebContext context) {
    CurrentContext.CURRENT.set(context);
  }

  public static void addControl(Control control) {
    if (CurrentContext.get() == null) {
      return;
    }
    CurrentContext.get().addControl(control);
  }

}
