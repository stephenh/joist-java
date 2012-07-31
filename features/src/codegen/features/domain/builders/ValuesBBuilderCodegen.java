package features.domain.builders;

import com.domainlanguage.time.TimePoint;
import features.domain.ValuesB;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class ValuesBBuilderCodegen extends AbstractBuilder<ValuesB> {

  public ValuesBBuilderCodegen(ValuesB instance) {
    super(instance);
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

  @Override
  public ValuesBBuilder defaults() {
    if (name() == null) {
      name("name");
    }
    return (ValuesBBuilder) super.defaults();
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
    if (UoW.isOpen()) {
      if (get().getChanged().size() == 0) {
        throw new RuntimeException("instance has not been changed yet");
      }
      UoW.flush();
    } else {
      throw new RuntimeException("ensureSaved only works if the UoW is open");
    }
    return (ValuesBBuilder) this;
  }

}
