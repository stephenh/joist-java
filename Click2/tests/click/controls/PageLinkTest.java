package click.controls;

import junit.framework.Assert;
import click.AbstractPage;
import click.pages.AddModelPublicFieldPage;
import click.pages.HelloWorldPage;

public class PageLinkTest extends AbstractClickControlTest {

    public void testNoParameters() {
        PageLink p = new PageLink(HelloWorldPage.class);
        Assert.assertEquals("/helloWorld.htm", p.getHref());
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
        Employee e = new Employee();
        e.id = 2;

        PageLink p = new PageLink(TwoStringFieldsPage.class);
        p.addParameter("bar");
        p.addParameter("value2", "baz");
        Assert.assertEquals("click/controls/pageLinkTest$TwoStringFields.htm?value1=bar&value2=baz", p.getHref());
    }

    public static class TwoStringFieldsPage extends AbstractPage {
        public String value1;
        public String value2;
    }

    public static class DomainObjectPage extends AbstractPage {
        public Employee employee;
    }

    public static class Employee {
        public Integer id;
    }
}
