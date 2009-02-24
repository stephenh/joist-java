package click;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class PageUrlResolver {

    private static final Pattern fileExtension = Pattern.compile("\\.[A-Za-z]+$");
    private final String basePackageName;

    public PageUrlResolver(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    /** @param path the path without the webapp context */
    public String getPageFromPath(String path) {
        if (path == null || !path.startsWith("/")) {
            throw new RuntimeException("uri must be non null and start with /");
        }
        // Add caching here
        List<String> parts = new ArrayList<String>(Arrays.asList(path.substring(1).split("/")));
        // Get the last part to muck with (cc.htm)
        String last = parts.remove(parts.size() - 1);
        // Trim file extension (cc.htm -> cc)
        last = PageUrlResolver.fileExtension.matcher(last).replaceAll("");
        // Cap it (cc -> Cc)
        last = StringUtils.capitalize(last);
        // Put it back at the end
        parts.add(parts.size(), last);
        return this.basePackageName + "." + StringUtils.join(parts, '.') + "Page";
    }

    /**
     * @param path a slash delimited uri, e.g. <code>/aa/bb/cc.htm</code>
     * @return <code>aa.bb.Cc</code>
     */
    public String getPathFromPage(String className) {
        // Add caching here
        String path = StringUtils.removeStart(className, this.basePackageName);
        path = path.replace('.', '/');
        path = StringUtils.removeEnd(path, "Page");
        int lastDot = path.lastIndexOf('/');
        path = StringUtils.replaceOnce(path, "/" + path.charAt(lastDot + 1), ("/" + path.charAt(lastDot + 1)).toLowerCase());
        path = path + ".htm";
        return path;
    }

    public String getTemplateFromPage(String className) {
        return "/" + StringUtils.removeEnd(className.replace(".", "/"), "Page") + ".htm";
    }
}
