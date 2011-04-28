package joist.perf.hibernate;

import java.util.Set;
import java.util.TreeSet;

public class Parent {

  private Integer id;
  private String name;
  private Integer version;
  private Set<Child> childs = new TreeSet<Child>();

  public Parent() {
  }

  public Parent(String name) {
    this.setName(name);
  }

  public Set<Child> getChilds() {
    return this.childs;
  }

  public void setChilds(Set<Child> childs) {
    this.childs = childs;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getVersion() {
    return this.version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }
}
