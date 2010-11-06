package features.domain.builders;

import com.domainlanguage.time.CalendarDate;
import features.domain.UserTypesAFoo;
import joist.domain.builders.AbstractBuilder;

@SuppressWarnings("all")
public abstract class UserTypesAFooBuilderCodegen extends AbstractBuilder<UserTypesAFoo> {

    public UserTypesAFooBuilderCodegen(UserTypesAFoo instance) {
        super(instance);
    }

    public CalendarDate created() {
        return get().getCreated();
    }

    public UserTypesAFooBuilder created(CalendarDate created) {
        get().setCreated(created);
        return (UserTypesAFooBuilder) this;
    }

    public UserTypesAFooBuilder with(CalendarDate created) {
        get().setCreated(created);
        return (UserTypesAFooBuilder) this;
    }

    public Integer id() {
        return get().getId();
    }

    public UserTypesAFooBuilder id(Integer id) {
        get().setId(id);
        return (UserTypesAFooBuilder) this;
    }

    public String name() {
        return get().getName();
    }

    public UserTypesAFooBuilder name(String name) {
        get().setName(name);
        return (UserTypesAFooBuilder) this;
    }

    public UserTypesAFooBuilder with(String name) {
        get().setName(name);
        return (UserTypesAFooBuilder) this;
    }

    public UserTypesAFoo get() {
        return (features.domain.UserTypesAFoo) super.get();
    }

}
