package click.util;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang.ObjectUtils;
import org.exigencecorp.util.StringBuilderr;

import click.Control;

/** Add HTML-specific helper methods to the {@link StringBuilderr} class.
 *
 * Besides <code>start</code>, <code>attribute</code>, etc., we override
 * <code>interpolate</code> to recognize replacements like:
 *
 *     sb.line("<tag attribute={}></tag>", value);
 *
 * And we'll auto-wrap value in double quotes because the immediately-
 * proceeding character to the <code>{}</code> replacement is <code>=</code>.
 */
public class HtmlWriter {

    private final Writer w;

    public HtmlWriter(Writer w) {
        this.w = w;
    }

    public void line(String line, Object... args) {
        this.append(line, args);
        this.append("\n");
    }

    public void append(String pattern, Object... args) {
        try {
            int arg = 0;
            int at = 0;
            int br;
            while ((br = pattern.indexOf("{}", at)) != -1) {
                this.w.write(pattern.substring(at, br));
                if (arg < args.length) {
                    boolean wrapInQuotes = br > 0 && pattern.charAt(br - 1) == '=';
                    if (wrapInQuotes) {
                        this.w.write('"');
                    }
                    Object value = args[arg++];
                    if (value instanceof Control) {
                        ((Control) value).render(this);
                    } else {
                        this.w.write(ObjectUtils.toString(value));
                    }
                    if (wrapInQuotes) {
                        this.w.write('"');
                    }
                }
                at = br + 2;
            }
            if (at != pattern.length()) {
                this.w.write(pattern.substring(at));
            }
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }

    public void start(String element) {
        this.append("<");
        this.append(element);
    }

    public void attribute(String name, String value) {
        this.append(" ");
        this.append(name);
        this.append("=\"");
        this.append(value);
        this.append("\"");
    }

    public void startDone() {
        this.append(">");
    }

    public void end(String element) {
        this.append("</");
        this.append(element);
        this.append(">");
    }

}
