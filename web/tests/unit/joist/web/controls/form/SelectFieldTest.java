package joist.web.controls.form;

import java.util.ArrayList;

import joist.web.controls.AbstractClickControlTest;
import joist.web.fakedomain.Employee;
import junit.framework.Assert;
import bindgen.java.util.ListBinding;
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

        EmployeeBinding b = new EmployeeBinding();
        ListBinding<Employee> l = new ListBinding<Employee>(new ArrayList<Employee>());
        Assert.assertNull(b.get());

        SelectField<Employee> s = new SelectField<Employee>(b, l).id("employee").options(new Employee(2)).multiple(true);
        s.onProcess();
        Assert.assertEquals(2, l.get().get(0).id.intValue());
        Assert.assertEquals(3, l.get().get(1).id.intValue());
    }

}
