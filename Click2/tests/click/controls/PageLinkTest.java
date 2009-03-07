package click.controls;

import junit.framework.Assert;

import org.exigencecorp.bindgen.Bindable;
import org.exigencecorp.conversion.AbstractConverter;
import org.exigencecorp.conversion.Converter;

import bindgen.click.controls.pageLinkTest.EmployeeBinding;
import click.AbstractPage;
import click.pages.AddModelPublicFieldPage;
import click.pages.HelloWorldPage;

public class PageLinkTest extends AbstractClickControlTest {

    public void testNoParameters() {
        PageLink p = new PageLink(HelloWorldPage.class);
        Assert.assertEquals("/helloWorld.htm", p.getHref());
    }

    public void testContentDefaultsToPageName() {
        PageLink p = new PageLink(HelloWorldPage.class);
        Assert.assertEquals("<a id=\"HelloWorld\" href=\"/helloWorld.htm\">Hello World</a>", p.toString());
    }

    public void testOneStringParameter() {
        PageLink p = new PageLink(AddModelPublicFieldPage.class);
        p.addParameter("bar");
        Assert.assertEquals("/addModelPublicField.htm?value=bar", p.getHref());
    }

    public void testTwoStringParametersMeansWeJustPickTheFirst() {
        PageLink p = new PageLink(TwoStringFieldsPage.class);
        p.addParameter("bar");
        Assert.assertEquals("click/controls/pageLinkTest$TwoStringFields.htm?value1=bar", p.getHref());
    }

    public void testTwoStringParametersGetTheSecondWithTheExplicitName() {
        PageLink p = new PageLink(TwoStringFieldsPage.class);
        p.addParameter("bar");
        p.addParameter("value2", "baz");
        Assert.assertEquals("click/controls/pageLinkTest$TwoStringFields.htm?value1=bar&value2=baz", p.getHref());
    }

    public void testParameterGetsConvertedToAString() {
        this.config.getUrlConverterRegistry().addConverter(PageLinkTest.employeeToString);
        PageLink p = new PageLink(EmployeePage.class);
        p.addParameter(new Employee(2));
        Assert.assertEquals("click/controls/pageLinkTest$Employee.htm?employee=2", p.getHref());
    }

    public void testBoundParameterGetsConvertedToAString() {
        this.config.getUrlConverterRegistry().addConverter(PageLinkTest.employeeToString);
        PageLink p = new PageLink(EmployeePage.class);
        EmployeeBinding b = new EmployeeBinding(new Employee(2));
        p.addParameter(b);
        Assert.assertEquals("click/controls/pageLinkTest$Employee.htm?employee=2", p.getHref());
    }

    public void testBoundParameterGetsConvertedToAStringWithExplicitName() {
        this.config.getUrlConverterRegistry().addConverter(PageLinkTest.employeeToString);
        PageLink p = new PageLink(EmployeePage.class);
        EmployeeBinding b = new EmployeeBinding(new Employee(2));
        p.addParameter("employee", b);
        Assert.assertEquals("click/controls/pageLinkTest$Employee.htm?employee=2", p.getHref());
    }

    public static class TwoStringFieldsPage extends AbstractPage {
        public String value1;
        public String value2;
    }

    public static class EmployeePage extends AbstractPage {
        public Employee employee;
    }

    @Bindable
    public static class Employee {
        public Integer id;

        public Employee(Integer id) {
            this.id = id;
        }
    }

    public static Converter<Employee, String> employeeToString = new AbstractConverter<Employee, String>() {
        public String convertOneToTwo(Employee value, Class<? extends String> toType) {
            return value.id.toString();
        }

        public Employee convertTwoToOne(String value, Class<? extends Employee> toType) {
            return new Employee(new Integer(value));
        }
    };
}
