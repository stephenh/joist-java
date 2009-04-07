package joist.web.controls.form;

import joist.web.controls.AbstractClickControlTest;
import joist.web.fakedomain.Employee;
import junit.framework.Assert;
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

}
