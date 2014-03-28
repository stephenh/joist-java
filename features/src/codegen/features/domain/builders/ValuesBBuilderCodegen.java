package features.domain.builders;

import com.domainlanguage.time.TimePoint;
import features.domain.ValuesB;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ValuesBBuilderCodegen extends AbstractBuilder<ValuesB> {

  public ValuesBBuilderCodegen(ValuesB instance) {
    super(instance);
  }

  @Override
  public ValuesBBuilder defaults() {
    try {
      DefaultsContext c = DefaultsContext.push();
      if (name() == null) {
        name("name");
      }
      return (ValuesBBuilder) super.defaults();
    } finally {
      DefaultsContext.pop();
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public ValuesBBuilder id(Long id) {
    get().setId(id);
    return (ValuesBBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public ValuesBBuilder name(String name) {
    get().setName(name);
    return (ValuesBBuilder) this;
  }

  public ValuesBBuilder with(String name) {
    return name(name);
  }

  public TimePoint start() {
    return get().getStart();
  }

  public ValuesBBuilder start(TimePoint start) {
    get().setStart(start);
    return (ValuesBBuilder) this;
  }

  public ValuesBBuilder with(TimePoint start) {
    return start(start);
  }

  public ValuesB get() {
    return (features.domain.ValuesB) super.get();
  }

  @Override
  public ValuesBBuilder ensureSaved() {
    doEnsureSaved();
    return (ValuesBBuilder) this;
  }

  @Override
  public void delete() {
    ValuesB.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = ValuesB.queries.findAllIds();
    for (Long id : ids) {
      ValuesB.queries.delete(ValuesB.queries.find(id));
    }
  }

}
