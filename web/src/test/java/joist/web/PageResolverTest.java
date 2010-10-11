package joist.web;

import junit.framework.Assert;

public class PageResolverTest extends AbstractClickTest {

  public void testRootDirectory() {
    PageResolver resolver = new PageResolver("app.pages");
    Assert.assertEquals("app.pages.AbcPage", resolver.getPageFromPath("/abc.htm"));
    Assert.assertEquals("/abc.htm", resolver.getPathFromPage("app.pages.AbcPage"));
    Assert.assertEquals("/app/pages/abc.htm", resolver.getTemplateFromPage("app.pages.AbcPage"));
  }

  public void testCamelCasing() {
    PageResolver resolver = new PageResolver("app.pages");
    Assert.assertEquals("app.pages.AbcFivePage", resolver.getPageFromPath("/abcFive.htm"));
    Assert.assertEquals("/abcFive.htm", resolver.getPathFromPage("app.pages.AbcFivePage"));
    Assert.assertEquals("/app/pages/abcFive.htm", resolver.getTemplateFromPage("app.pages.AbcFivePage"));
  }

  public void testTwoSubDirectories() {
    PageResolver resolver = new PageResolver("app.pages");
    Assert.assertEquals("app.pages.a.b.CPage", resolver.getPageFromPath("/a/b/c.htm"));
    Assert.assertEquals("/a/b/c.htm", resolver.getPathFromPage("app.pages.a.b.CPage"));
    Assert.assertEquals("/app/pages/a/b/c.htm", resolver.getTemplateFromPage("app.pages.a.b.CPage"));
  }

  public void testInnerClass() {
    PageResolver resolver = new PageResolver("app.pages");
    Assert.assertEquals("/app/pages/a/b/c.wrapper.htm", resolver.getTemplateFromPage("app.pages.a.b.CPage$Wrapper"));
  }

}
