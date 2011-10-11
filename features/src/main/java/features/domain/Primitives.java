package features.domain;

public class Primitives extends PrimitivesCodegen {

  public Primitives() {
  }

  public Primitives(String name) {
    this.setName(name);
    this.setFlag(false);
  }

  public Primitives flag(boolean flag) {
    this.setFlag(flag);
    return this;
  }

}
