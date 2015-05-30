package features.domain.builders;

import features.domain.InheritanceASubOne;
import features.domain.InheritanceASubTwo;
import features.domain.InheritanceAThing;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class InheritanceAThingBuilderCodegen extends AbstractBuilder<InheritanceAThing> {

  public InheritanceAThingBuilderCodegen(InheritanceAThing instance) {
    super(instance);
  }

  @Override
  public InheritanceAThingBuilder defaults() {
    return (InheritanceAThingBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (name() == null) {
      name(defaultName());
    }
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

  protected String defaultName() {
    return "name";
  }

  public InheritanceASubOneBuilder newInheritanceASubOne() {
    return Builders.aInheritanceASubOne().inheritanceAThing((InheritanceAThingBuilder) this);
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

  public InheritanceASubTwoBuilder newInheritanceASubTwo() {
    return Builders.aInheritanceASubTwo().inheritanceAThing((InheritanceAThingBuilder) this);
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

  @Override
  public InheritanceAThingBuilder ensureSaved() {
    doEnsureSaved();
    return (InheritanceAThingBuilder) this;
  }

  @Override
  public InheritanceAThingBuilder use(AbstractBuilder<?> builder) {
    return (InheritanceAThingBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    InheritanceAThing.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = InheritanceAThing.queries.findAllIds();
    for (Long id : ids) {
      InheritanceAThing.queries.delete(InheritanceAThing.queries.find(id));
    }
  }

}
