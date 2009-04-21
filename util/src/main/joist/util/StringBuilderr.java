package joist.util;

public class StringBuilderr {

    private StringBuilder sb = new StringBuilder();

    /**
     * @param line pattern <code>{}</code> -> <code>args[i]</code> and <code>'</code> -> <code>"</code> (only if there are args)
     * @param args objects to replace <code>{}</code>
     */
    public void line(String line, Object... args) {
        this.append(line, args);
        this.append("\n");
    }

    /**
     * @param indent number of levels to indent
     * @param line pattern <code>{}</code> -> <code>args[i]</code> and <code>'</code> -> <code>"</code> (only if there are args)
     * @param args objects to replace <code>{}</code>
     */
    public void line(int indent, String line, Object... args) {
        this.append(this.repeat("    ", indent));
        this.append(line, args);
        this.append("\n");
    }

    public void lineIfNeeded() {
        if (this.sb.charAt(this.sb.length() - 1) != '\n') {
            this.sb.append("\n");
        }
    }

    /**
     * @param line pattern <code>{}</code> -> <code>args[i]</code> and <code>'</code> -> <code>"</code> (only if there are args)
     * @param args objects to replace <code>{}</code>
     */
    public void append(String pattern, Object... args) {
        this.sb.append(this.interpolate(pattern, args));
    }

    public void stripLastCharacterOnPreviousLine() {
        int length = this.sb.length();
        this.sb.delete(length - 2, length - 1);
    }

    public void stripLastCommaSpace() {
        int length = this.sb.length();
        this.sb.delete(length - 2, length);
    }

    public StringBuilderr stripTrailingNewLine() {
        int length = this.sb.length();
        if (this.sb.charAt(length - 1) == '\n') {
            this.sb.delete(length - 1, length);
        }
        return this;
    }

    /** Appends <code>string</code> but indents all of its non-empty lines by <code>indent</code>. */
    public StringBuilderr append(int indent, String string) {
        String prefix = this.repeat("    ", indent);
        int at = 0;
        int br;
        while ((br = string.indexOf("\n", at)) != -1) {
            boolean skipPrefix = br > 0 && string.charAt(br - 1) == '\n';
            if (!skipPrefix) {
                this.sb.append(prefix);
            }
            this.sb.append(string.substring(at, br + 1));
            at = br + 1;
        }
        if (at < string.length()) {
            this.sb.append(prefix);
            this.sb.append(string.substring(at));
        }
        return this;
    }

    public void line() {
        this.sb.append("\n");
    }

    public String toString() {
        return this.sb.toString();
    }

    protected String interpolate(String pattern, Object... args) {
        return Interpolate.string(pattern, args);
    }

    private String repeat(String s, int times) {
        String r = "";
        for (int i = 0; i < times; i++) {
            r += s;
        }
        return r;
    }

}
