package joist.perf.hibernate;

public class GrandChild {

  private Integer id;
  private String name;
  private Child child;
  private Integer version;

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

  public Child getChild() {
    return this.child;
  }

  public void setChild(Child child) {
    this.child = child;
  }

}
