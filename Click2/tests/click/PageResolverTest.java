package click;

import junit.framework.Assert;
import junit.framework.TestCase;

public class PageResolverTest extends TestCase {

    public void testRootDirectory() {
        PageResolver resolver = new PageResolver("app.pages");
        Assert.assertEquals("app.pages.AbcPage", resolver.getPageFromPath("/abc.htm"));
        Assert.assertEquals("/abc.htm", resolver.getPathFromPage("app.pages.AbcPage"));
        Assert.assertEquals("/app/pages/abc.htm", resolver.getTemplateFromPage("app.pages.AbcPage"));
    }

    public void testTwoSubDirectories() {
        PageResolver resolver = new PageResolver("app.pages");
        Assert.assertEquals("app.pages.a.b.CPage", resolver.getPageFromPath("/a/b/c.htm"));
        Assert.assertEquals("/a/b/c.htm", resolver.getPathFromPage("app.pages.a.b.CPage"));
        Assert.assertEquals("/app/pages/a/b/c.htm", resolver.getTemplateFromPage("app.pages.a.b.CPage"));
    }

}
