package click.util;

import junit.framework.Assert;
import junit.framework.TestCase;
import click.PageUrlResolver;

public class ClassNameTest extends TestCase {

    public void testRootDirectory() {
        PageUrlResolver resolver = new PageUrlResolver("app.pages");
        Assert.assertEquals("app.pages.AbcPage", resolver.getPageFromPath("/abc.htm"));
        Assert.assertEquals("/abc.htm", resolver.getPathFromPage("app.pages.AbcPage"));
    }

    public void testTwoSubDirectories() {
        PageUrlResolver resolver = new PageUrlResolver("app.pages");
        Assert.assertEquals("app.pages.a.b.CPage", resolver.getPageFromPath("/a/b/c.htm"));
        Assert.assertEquals("/a/b/c.htm", resolver.getPathFromPage("app.pages.a.b.CPage"));
    }

}
