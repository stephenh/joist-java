package joist.web.controls;

import joist.web.AbstractPage;
import joist.web.CurrentContext;
import joist.web.DefaultPageProcessor;
import joist.web.controls.form.Form;
import junit.framework.Assert;

public class ContainerTest extends AbstractClickControlTest {

  private AbstractPage page;

  public void setUp() throws Exception {
    super.setUp();
    this.page = new AbstractPage() {
    };
  }

  public void testWithForm() {
    CurrentContext.get().setPage(this.page);
    Form f = new Form("f");
    new DefaultPageProcessor().doAddOrphanControlsToPage(this.page);
    Assert.assertEquals(true, this.page.getControls().contains(f));
  }

}
