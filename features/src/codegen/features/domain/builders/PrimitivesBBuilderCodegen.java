package features.domain.builders;

import features.domain.PrimitivesB;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class PrimitivesBBuilderCodegen extends AbstractBuilder<PrimitivesB> {

    public PrimitivesBBuilderCodegen(PrimitivesB instance) {
        super(instance);
    }

    public PrimitivesBBuilder big1(Long big1) {
        get().setBig1(big1);
        return (PrimitivesBBuilder) this;
    }

    public PrimitivesBBuilder big2(Long big2) {
        get().setBig2(big2);
        return (PrimitivesBBuilder) this;
    }

    public PrimitivesBBuilder bool1(Boolean bool1) {
        get().setBool1(bool1);
        return (PrimitivesBBuilder) this;
    }

    public PrimitivesBBuilder bool2(Boolean bool2) {
        get().setBool2(bool2);
        return (PrimitivesBBuilder) this;
    }

    public PrimitivesBBuilder id(Integer id) {
        get().setId(id);
        return (PrimitivesBBuilder) this;
    }

    public PrimitivesBBuilder int1(Integer int1) {
        get().setInt1(int1);
        return (PrimitivesBBuilder) this;
    }

    public PrimitivesBBuilder int2(Integer int2) {
        get().setInt2(int2);
        return (PrimitivesBBuilder) this;
    }

    public PrimitivesBBuilder small1(Short small1) {
        get().setSmall1(small1);
        return (PrimitivesBBuilder) this;
    }

    public PrimitivesBBuilder small2(Short small2) {
        get().setSmall2(small2);
        return (PrimitivesBBuilder) this;
    }

    public PrimitivesB get() {
        return (features.domain.PrimitivesB) super.get();
    }

}
