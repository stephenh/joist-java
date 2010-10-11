package joist.web.controls.form;

import java.util.List;

import joist.util.Copy;
import joist.web.controls.AbstractClickControlTest;
import joist.web.controls.form.selectFieldTest.PageBinding;
import joist.web.fakedomain.Employee;
import joist.web.fakedomain.EmployeeBinding;
import junit.framework.Assert;

import org.bindgen.Bindable;

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

  public void testOptionsTakesABinding() {
    Page p = new Page();
    p.employees = Copy.list(new Employee(2), new Employee(3));

    PageBinding b = new PageBinding(p);
    SelectField<Employee> s = new SelectField<Employee>(b.employee()).id("employee").options(b.employees());

    Assert.assertEquals(2, s.getOptionsPossiblyFromBinding().size());
    Assert.assertEquals(2, s.getOptionsPossiblyFromBinding().get(0).id.intValue());
    Assert.assertEquals(3, s.getOptionsPossiblyFromBinding().get(1).id.intValue());
  }

  @Bindable
  public static class Page {
    public Employee employee;
    public List<Employee> employees;
  }

}
