package joist.web.controls.table;

import joist.web.AbstractPage;
import joist.web.controls.AbstractClickControlTest;
import joist.web.fakedomain.Employee;
import joist.web.fakedomain.EmployeeBinding;
import joist.web.pages.HelloWorldPage;
import junit.framework.Assert;

public class PageLinkColumnTest extends AbstractClickControlTest {

  public void testNoParameters() {
    PageLinkColumn p = new PageLinkColumn(HelloWorldPage.class);
    Assert.assertEquals("<a id=\"HelloWorld-link\" href=\"/helloWorld.htm\">Hello World</a>", this.render(p));
  }

  public void testTextOverride() {
    PageLinkColumn p = new PageLinkColumn(HelloWorldPage.class).text("click here");
    Assert.assertEquals("<a id=\"HelloWorld-link\" href=\"/helloWorld.htm\">click here</a>", this.render(p));
  }

  public void testTextOverrideWithBinding() {
    EmployeeBinding eb = new EmployeeBinding(new Employee(2));
    PageLinkColumn p = new PageLinkColumn(HelloWorldPage.class).text(eb);
    Assert.assertEquals("<a id=\"HelloWorld-link\" href=\"/helloWorld.htm\">Bob2</a>", this.render(p));
  }

  public void testParameterGetsConvertedToAString() {
    PageLinkColumn p = new PageLinkColumn(EmployeePage.class).param(new Employee(2));
    Assert.assertEquals("<a id=\"Employee-link\" href=\"joist/web/controls/table/pageLinkColumnTest$Employee.htm?employee=2\">Employee</a>",//
      this.render(p));
  }

  public void testBoundParameterGetsConvertedToAString() {
    EmployeeBinding eb = new EmployeeBinding(new Employee(2));
    PageLinkColumn p = new PageLinkColumn(EmployeePage.class).param(eb);
    Assert.assertEquals("<a id=\"Employee-link\" href=\"joist/web/controls/table/pageLinkColumnTest$Employee.htm?employee=2\">Employee</a>",//
      this.render(p));
  }

  public static class EmployeePage extends AbstractPage {
    public Employee employee;
  }

}
