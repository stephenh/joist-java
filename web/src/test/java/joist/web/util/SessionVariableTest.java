package joist.web.util;

import joist.web.RedirectException;
import joist.web.controls.AbstractClickControlTest;
import joist.web.fakedomain.Employee;
import joist.web.pages.HelloWorldPage;
import junit.framework.Assert;

public class SessionVariableTest extends AbstractClickControlTest {

  public void testSetEmployee() {
    SessionVariable<Employee> e = new SessionVariable<Employee>(Employee.class, "ee");
    e.set(new Employee(1));
    Assert.assertEquals("1", this.request.getSession().getAttribute("ee"));
  }

  public void testGetEmployee() {
    SessionVariable<Employee> e = new SessionVariable<Employee>(Employee.class, "ee");
    Employee original = new Employee(1);
    e.set(original);

    // Show they are not the same instance
    Assert.assertNotSame(original, e.get());
    // But they point to the same ee
    Assert.assertEquals(1, e.get().id.intValue());
  }

  public void testGetAndRedirect() {
    SessionVariable<Employee> e = new SessionVariable<Employee>(Employee.class, "ee").redirectIfUnset(HelloWorldPage.class);
    Assert.assertEquals(null, e.get());
    try {
      e.getOrRedirect();
      Assert.fail();
    } catch (RedirectException re) {
      Assert.assertEquals("/helloWorld.htm", re.getUrl());
    }
  }

}
