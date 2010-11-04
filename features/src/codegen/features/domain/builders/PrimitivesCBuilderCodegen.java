package features.domain.builders;

import com.domainlanguage.money.Money;
import com.domainlanguage.time.TimePoint;
import features.domain.PrimitivesC;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class PrimitivesCBuilderCodegen extends AbstractBuilder<PrimitivesC> {

    public PrimitivesCBuilderCodegen(PrimitivesC instance) {
        super(instance);
    }

    public PrimitivesCBuilder dollarAmount(Money dollarAmount) {
        get().setDollarAmount(dollarAmount);
        return (PrimitivesCBuilder) this;
    }

    public PrimitivesCBuilder with(Money dollarAmount) {
        get().setDollarAmount(dollarAmount);
        return (PrimitivesCBuilder) this;
    }

    public PrimitivesCBuilder id(Integer id) {
        get().setId(id);
        return (PrimitivesCBuilder) this;
    }

    public PrimitivesCBuilder name(String name) {
        get().setName(name);
        return (PrimitivesCBuilder) this;
    }

    public PrimitivesCBuilder with(String name) {
        get().setName(name);
        return (PrimitivesCBuilder) this;
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
