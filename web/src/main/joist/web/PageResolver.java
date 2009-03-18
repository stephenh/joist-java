package joist.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/** Maps URLs to page classes and back. */
public class PageResolver {

    private static final Pattern fileExtension = Pattern.compile("\\.[A-Za-z]+$");
    private final String basePackageName;

    public PageResolver(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    /**
     * Maps a path to a class name.
     *
     * @param path a slash delimited path, e.g. <code>/aa/bb/cc.htm</code>
     * @return the page's class name, e.g. <code>basePackage.aa.bb.CcPage</code>
     */
    public String getPageFromPath(String path) {
        if (path == null || !path.startsWith("/")) {
            throw new RuntimeException("uri must be non null and start with /");
        }
        // Add caching here
        List<String> parts = new ArrayList<String>(Arrays.asList(path.substring(1).split("/")));
        // Get the last part to muck with (cc.htm)
        String last = parts.remove(parts.size() - 1);
        // Trim file extension (cc.htm -> cc)
        last = PageResolver.fileExtension.matcher(last).replaceAll("");
        // Cap it (cc -> Cc)
        last = StringUtils.capitalize(last);
        // Put it back at the end
        parts.add(parts.size(), last);
        return this.basePackageName + "." + StringUtils.join(parts, '.') + "Page";
    }

    /**
     * Maps a class name to a path.
     *
     * @param className a class name.g. <code>basePackage.aa.bb.CcPage</code>
     * @return the page's path, e.g. <code>/aa/bb/cc.htm</code>
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

    /**
     * Maps a class name to a Velocity template.
     *
     * @param className a class name.g. <code>basePackage.aa.bb.CcPage</code>
     * @return the page's template, e.g. <code>/basePackage/aa/bb/cc.htm</code>
     */
    public String getTemplateFromPage(String className) {
        // Add caching here
        String path = "/" + className;
        path = path.replace('.', '/');
        path = StringUtils.removeEnd(path, "Page");
        int lastDot = path.lastIndexOf('/');
        path = StringUtils.replaceOnce(path, "/" + path.charAt(lastDot + 1), ("/" + path.charAt(lastDot + 1)).toLowerCase());
        path = path + ".htm";
        return path;
    }
}
