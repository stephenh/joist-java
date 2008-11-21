package org.exigencecorp.util;

public class Stack {

    private Stack() {
    }

    /** @return the class name of our caller's caller */
    public static String getCallersClassName() {
        return new ClassContext().getClassName(0);
    }

    /** @param numberUpFromMe is how many of your callers to ignore--e.g. if you are a helper method and actually want your caller's caller. */
    public static String getCallersClassName(int numberUpFromMe) {
        return new ClassContext().getClassName(numberUpFromMe);
    }

    private static class ClassContext extends SecurityManager {
        private String getClassName(int numberUpFromMe) {
            // Add 1 for ClassContext.getClassName
            // Add 1 for ClassContext.access
            // Add 1 for Stack.getCallersClassName() or Stack.getCallersClassName(int)
            // Add 1 for our caller (we want to get to our caller's caller)
            // Thread.currentThread().getStackTrace()
            return this.getClassContext()[numberUpFromMe + 4].getName();
        }
    }

}
