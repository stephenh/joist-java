package joist.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/** Maps URLs to page classes and back. */
public class PageResolver {

  private static final Pattern fileExtension = Pattern.compile("\\.[A-Za-z]+$");
  private final ConcurrentHashMap<String, String> pageCache = new ConcurrentHashMap<String, String>();
  private final ConcurrentHashMap<String, String> pathCache = new ConcurrentHashMap<String, String>();
  private final ConcurrentHashMap<String, String> templateCache = new ConcurrentHashMap<String, String>();
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
    String pageClassName = this.pageCache.get(path);
    if (pageClassName == null) {
      pageClassName = this.resolvePageFromPath(path);
      this.pageCache.put(path, pageClassName);
    }
    return pageClassName;
  }

  /**
   * Maps a class name to a path.
   *
   * @param className a class name.g. <code>basePackage.aa.bb.CcPage</code>
   * @return the page's path, e.g. <code>/aa/bb/cc.htm</code>
   */
  public String getPathFromPage(String className) {
    String path = this.pathCache.get(className);
    if (path == null) {
      path = this.resolvePathFromPage(className);
      this.pathCache.put(className, path);
    }
    return path;
  }

  /**
   * Maps a class name to a Velocity template.
   *
   * @param className a class name.g. <code>basePackage.aa.bb.CcPage</code>
   * @return the page's template, e.g. <code>/basePackage/aa/bb/cc.htm</code>
   */
  public String getTemplateFromPage(String className) {
    String path = this.templateCache.get(className);
    if (path == null) {
      path = this.resolveTemplateFromPage(className);
      this.templateCache.put(className, path);
    }
    return path;
  }

  protected String resolvePageFromPath(String path) {
    // /foo/bar.htm -> [ "foo", "bar.htm" ]
    List<String> parts = new ArrayList<String>(Arrays.asList(path.substring(1).split("/")));
    // Get the file name, e.g. bar.htm
    String last = parts.remove(parts.size() - 1);
    // Trim extension, bar.htm -> bar
    last = PageResolver.fileExtension.matcher(last).replaceAll("");
    // Cap it, bar -> Bar
    last = StringUtils.capitalize(last);
    // Put the file name back on the end, [ "foo", "Bar" ]
    parts.add(parts.size(), last);
    return this.basePackageName + "." + StringUtils.join(parts, '.') + "Page";
  }

  protected String resolvePathFromPage(String className) {
    // Strip base package, com.webapp.pages.package.FooPage -> package.FooPage
    String path = StringUtils.removeStart(className, this.basePackageName);
    // Change to slashes, package.FooPage -> package/FooPage
    path = path.replace('.', '/');
    // Lower case the class name, /FooPage -> /fooPage
    int lastDot = path.lastIndexOf('/');
    path = StringUtils.replaceOnce(path, "/" + path.charAt(lastDot + 1), ("/" + path.charAt(lastDot + 1)).toLowerCase());
    // Remove page suffix, /fooPage -> /foo
    path = StringUtils.removeEnd(path, "Page");
    // Add htm suffix, /foo -> foo.htm
    path = path + ".htm";
    return path;
  }

  protected String resolveTemplateFromPage(String className) {
    // Change to slashes, package.FooPage -> /package/FooPage
    String path = "/" + className.replace('.', '/');
    // Lower case the class name, /FooPage -> /fooPage
    int lastDot = path.lastIndexOf('/');
    path = StringUtils.replaceOnce(path, "/" + path.charAt(lastDot + 1), ("/" + path.charAt(lastDot + 1)).toLowerCase());
    // Lower case inner class names too, /foo$Bar -> /foo.bar
    int lastDollar = path.lastIndexOf('$');
    if (lastDollar != -1) {
      path = StringUtils.replaceOnce(path, "$" + path.charAt(lastDollar + 1), ("." + path.charAt(lastDollar + 1)).toLowerCase());
    }
    // Remove page suffix, /package/fooPage -> /package/foo
    int pageIndex = path.lastIndexOf("Page");
    if (pageIndex != -1) {
      path = StringUtils.substring(path, 0, pageIndex) + StringUtils.substring(path, pageIndex + 4);
    }
    // Add htm suffix, /package/foo -> /package/foo.htm
    path = path + ".htm";
    return path;
  }

}
