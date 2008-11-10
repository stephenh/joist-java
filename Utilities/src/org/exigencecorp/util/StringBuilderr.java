package org.exigencecorp.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class StringBuilderr {

    private StringBuilder sb = new StringBuilder();

    public void line(String line, Object... args) {
        this.line(0, line, args);
    }

    public void lineIfNotNull(String line, Object... args) {
        if (line != null) {
            this.line(0, line, args);
        }
    }

    /**
     * @param indent number of levels to indent
     * @param line pattern <code>{}</code> -> <code>args[i]</code> and <code>'</code> -> <code>"</code> (only if there are args)
     * @param args objects to replace <code>{}</code>
     */
    public void line(int indent, String line, Object... args) {
        this.sb.append(StringUtils.repeat("    ", indent));
        this.append(line, args);
        this.sb.append("\n");
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
        for (Object arg : args) {
            pattern = StringUtils.replaceOnce(pattern, "{}", ObjectUtils.toString(arg));
        }
        this.sb.append(pattern);
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

    public StringBuilderr append(int indent, String string) {
        String prefix = StringUtils.repeat("    ", indent);
        String[] lines = StringUtils.splitPreserveAllTokens(string, "\n");
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() > 0) {
                lines[i] = prefix + lines[i];
            }
        }
        this.sb.append(StringUtils.join(lines, "\n"));
        return this;
    }

    public void line() {
        this.sb.append("\n");
    }

    public String toString() {
        return this.sb.toString();
    }

}
