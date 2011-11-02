package features.domain.builders;

import joist.domain.builders.AbstractBuilder;

import com.domainlanguage.money.Money;
import com.domainlanguage.time.TimePoint;

import features.domain.PrimitivesC;

@SuppressWarnings("all")
public abstract class PrimitivesCBuilderCodegen extends AbstractBuilder<PrimitivesC> {

  public PrimitivesCBuilderCodegen(PrimitivesC instance) {
    super(instance);
  }

  public Money dollarAmount() {
    return get().getDollarAmount();
  }

  public PrimitivesCBuilder dollarAmount(Money dollarAmount) {
    get().setDollarAmount(dollarAmount);
    return (PrimitivesCBuilder) this;
  }

  public PrimitivesCBuilder with(Money dollarAmount) {
    get().setDollarAmount(dollarAmount);
    return (PrimitivesCBuilder) this;
  }

  public Long id() {
    return get().getId();
  }

  public PrimitivesCBuilder id(Long id) {
    get().setId(id);
    return (PrimitivesCBuilder) this;
  }

  public String name() {
    return get().getName();
  }

  public PrimitivesCBuilder name(String name) {
    get().setName(name);
    return (PrimitivesCBuilder) this;
  }

  public PrimitivesCBuilder with(String name) {
    get().setName(name);
    return (PrimitivesCBuilder) this;
  }

  public TimePoint timestamp() {
    return get().getTimestamp();
  }

  public PrimitivesCBuilder timestamp(TimePoint timestamp) {
    get().setTimestamp(timestamp);
    return (PrimitivesCBuilder) this;
  }

  public PrimitivesCBuilder with(TimePoint timestamp) {
    get().setTimestamp(timestamp);
    return (PrimitivesCBuilder) this;
  }

  public PrimitivesC get() {
    return (features.domain.PrimitivesC) super.get();
  }

}
