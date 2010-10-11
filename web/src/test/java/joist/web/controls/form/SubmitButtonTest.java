package joist.web.controls.form;

import joist.web.controls.AbstractClickControlTest;
import junit.framework.Assert;

public class SubmitButtonTest extends AbstractClickControlTest {

  public void testNoopIfNoButtonOrDefault() {
    FakeRun run = new FakeRun();
    SubmitButton submit = new SubmitButton(run);
    submit.onProcess();
    Assert.assertFalse(run.ran);
  }

  public void testRunsIfDefault() {
    FakeRun run = new FakeRun();
    SubmitButton submit = new SubmitButton(run);
    submit.setDefaultButton(true);
    submit.onProcess();
    Assert.assertTrue(run.ran);
  }

  public void testRunsIfSubmitted() {
    FakeRun run = new FakeRun();
    SubmitButton submit = new SubmitButton(run);
    submit.setLabel("Foo");
    this.request.setParameter("_formButton", "Foo");
    submit.onProcess();
    Assert.assertTrue(run.ran);
  }

  public void testNoopIfSubmittedWithWrongLabel() {
    FakeRun run = new FakeRun();
    SubmitButton submit = new SubmitButton(run);
    submit.setLabel("Foo");
    this.request.setParameter("_formButton", "FooBar");
    submit.onProcess();
    Assert.assertFalse(run.ran);
  }

  public void testOnlyFirstButtonIsDefault() {
    SubmitButton one = new SubmitButton(null);
    SubmitButton two = new SubmitButton(null);
    Assert.assertFalse(one.isDefaultButton());
    Assert.assertFalse(two.isDefaultButton());

    Form f = new Form("f");
    f.add(one);
    f.add(two);

    Assert.assertTrue(one.isDefaultButton());
    Assert.assertFalse(two.isDefaultButton());
  }

  public static class FakeRun implements Runnable {
    public boolean ran;

    public void run() {
      this.ran = true;
    }
  }
}
