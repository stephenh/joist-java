package joist.web.fakedomain;

import joist.converter.AbstractConverter;

public class EmployeeToStringConverter extends AbstractConverter<Employee, String> {

  public String convertOneToTwo(Employee value, Class<? extends String> toType) {
    return value.id.toString();
  }

  public Employee convertTwoToOne(String value, Class<? extends Employee> toType) {
    return new Employee(new Integer(value));
  }

}
