package click.controls.table;

import junit.framework.Assert;
import bindgen.click.fakedomain.EmployeeBinding;
import click.AbstractPage;
import click.controls.AbstractClickControlTest;
import click.fakedomain.Employee;
import click.pages.HelloWorldPage;

public class PageLinkColumnTest extends AbstractClickControlTest {

    public void testNoParameters() {
        PageLinkColumn p = new PageLinkColumn(HelloWorldPage.class);
        Assert.assertEquals("<a id=\"HelloWorld\" href=\"/helloWorld.htm\">Hello World</a>", this.render(p));
    }

    public void testTextOverride() {
        PageLinkColumn p = new PageLinkColumn(HelloWorldPage.class);
        p.setText("click here");
        Assert.assertEquals("<a id=\"HelloWorld\" href=\"/helloWorld.htm\">click here</a>", this.render(p));
    }

    public void testTextOverrideWithBinding() {
        EmployeeBinding eb = new EmployeeBinding(new Employee(2));
        PageLinkColumn p = new PageLinkColumn(HelloWorldPage.class);
        p.setText(eb);
        Assert.assertEquals("<a id=\"HelloWorld\" href=\"/helloWorld.htm\">Bob2</a>", this.render(p));
    }

    public void testParameterGetsConvertedToAString() {
        PageLinkColumn p = new PageLinkColumn(EmployeePage.class).param(new Employee(2));
        Assert.assertEquals("<a id=\"Employee\" href=\"click/controls/table/pageLinkColumnTest$Employee.htm?employee=2\">Employee</a>",//
            this.render(p));
    }

    public void testBoundParameterGetsConvertedToAString() {
        EmployeeBinding eb = new EmployeeBinding(new Employee(2));
        PageLinkColumn p = new PageLinkColumn(EmployeePage.class).param(eb);
        Assert.assertEquals("<a id=\"Employee\" href=\"click/controls/table/pageLinkColumnTest$Employee.htm?employee=2\">Employee</a>",//
            this.render(p));
    }

    public static class EmployeePage extends AbstractPage {
        public Employee employee;
    }

}
