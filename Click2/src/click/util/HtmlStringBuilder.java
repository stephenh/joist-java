package click.util;

import org.apache.commons.lang.StringUtils;

public class HtmlStringBuilder {

    private StringBuilder sb = new StringBuilder();

    public String toString() {
        return this.sb.toString();
    }

    public void append(String text, Object... args) {
        for (Object arg : args) {
            int at = text.indexOf("{}");
            if (at > 0 && text.charAt(at - 1) == '=') {
                arg = "\"" + arg + "\"";
            }
            text = StringUtils.replaceOnce(text, "{}", String.valueOf(arg));
        }
        this.sb.append(text);
    }

    public void appendln(String text, Object... args) {
        this.append(text, args);
        this.sb.append("\n");
    }

    public void start(String element) {
        this.sb.append("<");
        this.sb.append(element);
    }

    public void attribute(String name, String value) {
        this.sb.append(" ");
        this.sb.append(name);
        this.sb.append("=");
        this.sb.append(value);
    }

    public void startDone() {
        this.sb.append(">");
    }

    public void end(String element) {
        this.sb.append("</");
        this.sb.append(element);
        this.sb.append(">");
    }
}
