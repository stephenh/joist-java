package features.domain.builders;

import com.domainlanguage.money.Money;
import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.TimePoint;
import features.domain.PrimitivesC;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class PrimitivesCBuilderCodegen extends AbstractBuilder<PrimitivesC> {

  public PrimitivesCBuilderCodegen(PrimitivesC instance) {
    super(instance);
  }

  @Override
  public PrimitivesCBuilder defaults() {
    return (PrimitivesCBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (day() == null) {
      day(defaultDay());
    }
    if (dollarAmount() == null) {
      dollarAmount(defaultDollarAmount());
    }
    if (name() == null) {
      name(defaultName());
    }
    if (timestamp() == null) {
      timestamp(defaultTimestamp());
    }
  }

  public CalendarDate day() {
    return get().getDay();
  }

  public PrimitivesCBuilder day(CalendarDate day) {
    get().setDay(day);
    return (PrimitivesCBuilder) this;
  }

  public PrimitivesCBuilder with(CalendarDate day) {
    return day(day);
  }

  protected CalendarDate defaultDay() {
    return CalendarDate.from(1970, 1, 1);
  }

  public Money dollarAmount() {
    return get().getDollarAmount();
  }

  public PrimitivesCBuilder dollarAmount(Money dollarAmount) {
    get().setDollarAmount(dollarAmount);
    return (PrimitivesCBuilder) this;
  }

  public PrimitivesCBuilder with(Money dollarAmount) {
    return dollarAmount(dollarAmount);
  }

  protected Money defaultDollarAmount() {
    return Money.dollars(0);
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
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
    return name(name);
  }

  protected String defaultName() {
    return "name";
  }

  public TimePoint timestamp() {
    return get().getTimestamp();
  }

  public PrimitivesCBuilder timestamp(TimePoint timestamp) {
    get().setTimestamp(timestamp);
    return (PrimitivesCBuilder) this;
  }

  public PrimitivesCBuilder with(TimePoint timestamp) {
    return timestamp(timestamp);
  }

  protected TimePoint defaultTimestamp() {
    return TimePoint.from(0);
  }

  public PrimitivesC get() {
    return (features.domain.PrimitivesC) super.get();
  }

  @Override
  public PrimitivesCBuilder ensureSaved() {
    doEnsureSaved();
    return (PrimitivesCBuilder) this;
  }

  @Override
  public PrimitivesCBuilder use(AbstractBuilder<?> builder) {
    return (PrimitivesCBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    PrimitivesC.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = PrimitivesC.queries.findAllIds();
    for (Long id : ids) {
      PrimitivesC.queries.delete(PrimitivesC.queries.find(id));
    }
  }

}
