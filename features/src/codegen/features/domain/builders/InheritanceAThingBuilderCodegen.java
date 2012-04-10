package features.domain.builders;

import features.domain.InheritanceASubOne;
import features.domain.InheritanceASubTwo;
import features.domain.InheritanceAThing;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceAThingBuilderCodegen extends AbstractBuilder<InheritanceAThing> {

  public InheritanceAThingBuilderCodegen(InheritanceAThing instance) {
    super(instance);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public InheritanceAThingBuilder id(Long id) {
    get().setId(id);
    return (InheritanceAThingBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public InheritanceAThingBuilder name(String name) {
    get().setName(name);
    return (InheritanceAThingBuilder) this;
  }

  public InheritanceAThingBuilder with(String name) {
    return name(name);
  }

  @Override
  public InheritanceAThingBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (InheritanceAThingBuilder) super.defaults();
  }

  public List<InheritanceASubOneBuilder> inheritanceASubOnes() {
    List<InheritanceASubOneBuilder> b = new ArrayList<InheritanceASubOneBuilder>();
    for (InheritanceASubOne e : get().getInheritanceASubOnes()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public InheritanceASubOneBuilder inheritanceASubOne(int i) {
    return Builders.existing(get().getInheritanceASubOnes().get(i));
  }

  public List<InheritanceASubTwoBuilder> inheritanceASubTwos() {
    List<InheritanceASubTwoBuilder> b = new ArrayList<InheritanceASubTwoBuilder>();
    for (InheritanceASubTwo e : get().getInheritanceASubTwos()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public InheritanceASubTwoBuilder inheritanceASubTwo(int i) {
    return Builders.existing(get().getInheritanceASubTwos().get(i));
  }

  public InheritanceAThing get() {
    return (features.domain.InheritanceAThing) super.get();
  }

}
