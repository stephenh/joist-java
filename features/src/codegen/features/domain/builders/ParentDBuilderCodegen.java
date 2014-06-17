package features.domain.builders;

import features.domain.ParentD;
import features.domain.ParentDChildA;
import features.domain.ParentDChildB;
import java.util.ArrayList;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ParentDBuilderCodegen extends AbstractBuilder<ParentD> {

  public ParentDBuilderCodegen(ParentD instance) {
    super(instance);
  }

  @Override
  public ParentDBuilder defaults() {
    return (ParentDBuilder) super.defaults();
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

  public ParentDBuilder id(Long id) {
    get().setId(id);
    return (ParentDBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ParentDBuilder name(String name) {
    get().setName(name);
    return (ParentDBuilder) this;
  }

  public ParentDBuilder with(String name) {
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public ParentDChildABuilder newParentDChildA() {
    return Builders.aParentDChildA().parentD((ParentDBuilder) this);
  }

  public List<ParentDChildABuilder> parentDChildAs() {
    UoW.flush();
    List<ParentDChildABuilder> b = new ArrayList<ParentDChildABuilder>();
    for (Long id : ParentD.queries.findParentDChildAsIds(get())) {
      b.add(Builders.theParentDChildA(id));
    }
    return b;
  }

  public ParentDChildABuilder parentDChildA(int i) {
    return parentDChildAs().get(i);
  }

  public ParentDChildBBuilder newParentDChildB() {
    return Builders.aParentDChildB().parentD((ParentDBuilder) this);
  }

  public List<ParentDChildBBuilder> parentDChildBs() {
    List<ParentDChildBBuilder> b = new ArrayList<ParentDChildBBuilder>();
    for (ParentDChildB e : get().getParentDChildBs()) {
      b.add(Builders.existing(e));
    }
    return b;
  }

  public ParentDChildBBuilder parentDChildB(int i) {
    return Builders.existing(get().getParentDChildBs().get(i));
  }

  public ParentD get() {
    return (features.domain.ParentD) super.get();
  }

  @Override
  public ParentDBuilder ensureSaved() {
    doEnsureSaved();
    return (ParentDBuilder) this;
  }

  @Override
  public ParentDBuilder use(AbstractBuilder<?> builder) {
    return (ParentDBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    ParentD.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ParentD.queries.findAllIds();
    for (Long id : ids) {
      ParentD.queries.delete(ParentD.queries.find(id));
    }
  }

}
