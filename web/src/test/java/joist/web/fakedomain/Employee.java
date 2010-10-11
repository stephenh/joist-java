package joist.web.fakedomain;

import org.bindgen.Bindable;

@Bindable
public class Employee {
  public Integer id;

  public Employee(Integer id) {
    this.id = id;
  }

  public String toFriendlyString() {
    return "Bob" + this.id;
  }
}
