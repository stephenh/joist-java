package click.util;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.util.StringBuilderr;

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
public class HtmlStringBuilderr extends StringBuilderr {

    public void start(String element) {
        this.append("<");
        this.append(element);
    }

    public void attribute(String name, String value) {
        this.append(" ");
        this.append(name);
        this.append("=");
        this.append(value);
    }

    public void startDone() {
        this.append(">");
    }

    public void end(String element) {
        this.append("</");
        this.append(element);
        this.append(">");
    }

    @Override
    protected String interpolate(String pattern, Object... args) {
        String fixed = pattern;
        for (Object arg : args) {
            int at = fixed.indexOf("{}");
            if (at > 0 && fixed.charAt(at - 1) == '=') {
                arg = "\"" + arg + "\"";
            }
            fixed = StringUtils.replaceOnce(fixed, "{}", String.valueOf(arg));
        }
        return fixed;
    }

}
