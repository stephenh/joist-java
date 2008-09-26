package click.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ClassName {

    private ClassName() {
    }

    /**
     * @param uri a slash delimited uri, e.g. <code>/aa/bb/cc.htm</code>
     * @return <code>aa.bb.Cc</code>
     */
    public static String fromUri(String uri) {
        if (uri == null || !uri.startsWith("/")) {
            throw new RuntimeException("uri must be non null and start with /");
        }

        List<String> parts = new ArrayList<String>(Arrays.asList(uri.split("/")));

        // Ignore first part that is ""
        parts.remove(0);

        String last = parts.remove(parts.size() - 1);
        // Trim file extension (cc.htm -> cc)
        last = last.replaceAll("\\.[a-z]+$", "");
        // Cap it (cc -> Cc)
        last = StringUtils.capitalize(last);
        // Put it back at the end
        parts.add(parts.size(), last);

        return StringUtils.join(parts, '.');
    }

}
