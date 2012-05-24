package features.domain.builders;

import com.domainlanguage.time.CalendarDate;
import features.domain.UserTypesAFoo;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

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
    return created(created);
  }

  @Override
  public UserTypesAFooBuilder defaults() {
    if (created() == null) {
      created(CalendarDate.from(1970, 1, 1));
    }
    if (name() == null) {
      name("name");
    }
    return (UserTypesAFooBuilder) super.defaults();
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public UserTypesAFooBuilder id(Long id) {
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
    return name(name);
  }

  public UserTypesAFoo get() {
    return (features.domain.UserTypesAFoo) super.get();
  }

  @Override
  public UserTypesAFooBuilder ensureSaved() {
    if (UoW.isOpen()) {
      if (get().getChanged().size() == 0) {
        throw new RuntimeException("instance has not been changed yet");
      }
      UoW.flush();
    } else {
      throw new RuntimeException("ensureSaved only works if the UoW is open");
    }
    return (UserTypesAFooBuilder) this;
  }

}
