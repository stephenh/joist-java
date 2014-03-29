package features.domain.builders;

import com.domainlanguage.time.CalendarDate;
import features.domain.UserTypesAFoo;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class UserTypesAFooBuilderCodegen extends AbstractBuilder<UserTypesAFoo> {

  public UserTypesAFooBuilderCodegen(UserTypesAFoo instance) {
    super(instance);
  }

  @Override
  public UserTypesAFooBuilder defaults() {
    return (UserTypesAFooBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (created() == null) {
      created(defaultCreated());
    }
    if (name() == null) {
      name(defaultName());
    }
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

  protected CalendarDate defaultCreated() {
    return CalendarDate.from(1970, 1, 1);
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

  protected String defaultName() {
    return "name";
  }

  public UserTypesAFoo get() {
    return (features.domain.UserTypesAFoo) super.get();
  }

  @Override
  public UserTypesAFooBuilder ensureSaved() {
    doEnsureSaved();
    return (UserTypesAFooBuilder) this;
  }

  @Override
  public UserTypesAFooBuilder use(AbstractBuilder<?> builder) {
    return (UserTypesAFooBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    UserTypesAFoo.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = UserTypesAFoo.queries.findAllIds();
    for (Long id : ids) {
      UserTypesAFoo.queries.delete(UserTypesAFoo.queries.find(id));
    }
  }

}
