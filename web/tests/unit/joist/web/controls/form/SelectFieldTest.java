package joist.web.controls.form;

import java.util.List;

import joist.web.controls.AbstractClickControlTest;
import joist.web.fakedomain.Employee;
import junit.framework.Assert;

import org.exigencecorp.bindgen.Bindable;

import bindgen.joist.web.controls.form.selectFieldTest.PageBinding;
import bindgen.joist.web.fakedomain.EmployeeBinding;

public class SelectFieldTest extends AbstractClickControlTest {

    public void testUsesUrlBinding() {
        this.request.setParameter("employee", "2");

        EmployeeBinding b = new EmployeeBinding();
        Assert.assertNull(b.get());

        SelectField<Employee> s = new SelectField<Employee>(b).id("employee").options(new Employee(2));
        s.onProcess();
        Assert.assertEquals(2, b.get().id.intValue());
    }

    public void testMultiple() {
        this.request.setParameter("employee", "2", "3");

        Page p = new Page();
        PageBinding b = new PageBinding(p);
        SelectField<Employee> s = new SelectField<Employee>(b.employees()).id("employee").options(new Employee(2)).multiple(true);
        s.onProcess();
        Assert.assertEquals(2, p.employees.get(0).id.intValue());
        Assert.assertEquals(3, p.employees.get(1).id.intValue());
    }

    @Bindable
    public static class Page {
        public List<Employee> employees;
    }

}
