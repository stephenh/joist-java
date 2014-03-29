package features.domain.builders;

import features.domain.PrimitivesB;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class PrimitivesBBuilderCodegen extends AbstractBuilder<PrimitivesB> {

  public PrimitivesBBuilderCodegen(PrimitivesB instance) {
    super(instance);
  }

  @Override
  public PrimitivesBBuilder defaults() {
    return (PrimitivesBBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (big2() == null) {
      big2(defaultBig2());
    }
    if (bool2() == null) {
      bool2(defaultBool2());
    }
    if (boolWithDefaultTrue() == null) {
      boolWithDefaultTrue(defaultBoolWithDefaultTrue());
    }
    if (int2() == null) {
      int2(defaultInt2());
    }
    if (small2() == null) {
      small2(defaultSmall2());
    }
  }

  public Long big1() {
    return get().getBig1();
  }

  public PrimitivesBBuilder big1(Long big1) {
    get().setBig1(big1);
    return (PrimitivesBBuilder) this;
  }

  public Long big2() {
    return get().getBig2();
  }

  public PrimitivesBBuilder big2(Long big2) {
    get().setBig2(big2);
    return (PrimitivesBBuilder) this;
  }

  protected Long defaultBig2() {
    return 0l;
  }

  public Boolean bool1() {
    return get().getBool1();
  }

  public PrimitivesBBuilder bool1(Boolean bool1) {
    get().setBool1(bool1);
    return (PrimitivesBBuilder) this;
  }

  public Boolean bool2() {
    return get().getBool2();
  }

  public PrimitivesBBuilder bool2(Boolean bool2) {
    get().setBool2(bool2);
    return (PrimitivesBBuilder) this;
  }

  protected Boolean defaultBool2() {
    return false;
  }

  public Boolean boolNullableWithDefaultFalse() {
    return get().getBoolNullableWithDefaultFalse();
  }

  public PrimitivesBBuilder boolNullableWithDefaultFalse(Boolean boolNullableWithDefaultFalse) {
    get().setBoolNullableWithDefaultFalse(boolNullableWithDefaultFalse);
    return (PrimitivesBBuilder) this;
  }

  public Boolean boolWithDefaultTrue() {
    return get().getBoolWithDefaultTrue();
  }

  public PrimitivesBBuilder boolWithDefaultTrue(Boolean boolWithDefaultTrue) {
    get().setBoolWithDefaultTrue(boolWithDefaultTrue);
    return (PrimitivesBBuilder) this;
  }

  protected Boolean defaultBoolWithDefaultTrue() {
    return false;
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public PrimitivesBBuilder id(Long id) {
    get().setId(id);
    return (PrimitivesBBuilder) this;
  }

  public Integer int1() {
    return get().getInt1();
  }

  public PrimitivesBBuilder int1(Integer int1) {
    get().setInt1(int1);
    return (PrimitivesBBuilder) this;
  }

  public Integer int2() {
    return get().getInt2();
  }

  public PrimitivesBBuilder int2(Integer int2) {
    get().setInt2(int2);
    return (PrimitivesBBuilder) this;
  }

  protected Integer defaultInt2() {
    return 0;
  }

  public Short small1() {
    return get().getSmall1();
  }

  public PrimitivesBBuilder small1(Short small1) {
    get().setSmall1(small1);
    return (PrimitivesBBuilder) this;
  }

  public Short small2() {
    return get().getSmall2();
  }

  public PrimitivesBBuilder small2(Short small2) {
    get().setSmall2(small2);
    return (PrimitivesBBuilder) this;
  }

  protected Short defaultSmall2() {
    return (short) 0;
  }

  public PrimitivesB get() {
    return (features.domain.PrimitivesB) super.get();
  }

  @Override
  public PrimitivesBBuilder ensureSaved() {
    doEnsureSaved();
    return (PrimitivesBBuilder) this;
  }

  @Override
  public PrimitivesBBuilder use(AbstractBuilder<?> builder) {
    return (PrimitivesBBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    PrimitivesB.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = PrimitivesB.queries.findAllIds();
    for (Long id : ids) {
      PrimitivesB.queries.delete(PrimitivesB.queries.find(id));
    }
  }

}
