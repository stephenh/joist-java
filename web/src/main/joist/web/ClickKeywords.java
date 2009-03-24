package joist.web;

import joist.web.controls.table.Table;

public class ClickKeywords {

    public static void flash(String key, Object value) {
        CurrentContext.get().getFlash().put(key, value);
    }

    public static void redirect(String url) {
        throw new RedirectException(url);
    }

    public static <T> Table<T> table(String name) {
        return new Table<T>(name);
    }

    public static void addModel(String key, Object value) {
        CurrentContext.get().getModel().put(key, value);
    }

    public static Object getSession(String key) {
        return CurrentContext.get().getRequest().getSession().getAttribute(key);
    }

    public static void setSession(String key, Object value) {
        CurrentContext.get().getRequest().getSession().setAttribute(key, value);
    }

}
