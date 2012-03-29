package features.domain.builders;

import features.domain.Child;
import features.domain.Parent;

public class ChildBuilder extends ChildBuilderCodegen {

  public ChildBuilder(Child instance) {
    super(instance);
  }

  @Override
  public ChildBuilder parent(Parent parent) {
    // a silly side affect so we can test this method gets called
    this.name("foo");
    return super.parent(parent);
  }

}
