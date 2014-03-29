package features.domain.builders;

import com.domainlanguage.time.TimePoint;
import features.domain.HistoryEntry;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.builders.DefaultsContext;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class HistoryEntryBuilderCodegen extends AbstractBuilder<HistoryEntry> {

  public HistoryEntryBuilderCodegen(HistoryEntry instance) {
    super(instance);
  }

  @Override
  public HistoryEntryBuilder defaults() {
    return (HistoryEntryBuilder) super.defaults();
  }

  @Override
  protected void defaults(DefaultsContext c) {
    super.defaults(c);
    if (primaryKey() == null) {
      primaryKey(defaultPrimaryKey());
    }
    if (rootTableName() == null) {
      rootTableName(defaultRootTableName());
    }
    if (type() == null) {
      type(defaultType());
    }
    if (updateTime() == null) {
      updateTime(defaultUpdateTime());
    }
  }

  public Long id() {
    if (UoW.isOpen() && get().getId() == null) {
      UoW.flush();
    }
    return get().getId();
  }

  public HistoryEntryBuilder id(Long id) {
    get().setId(id);
    return (HistoryEntryBuilder) this;
  }

  public String newValue() {
    return get().getNewValue();
  }

  public HistoryEntryBuilder newValue(String newValue) {
    get().setNewValue(newValue);
    return (HistoryEntryBuilder) this;
  }

  public String oldValue() {
    return get().getOldValue();
  }

  public HistoryEntryBuilder oldValue(String oldValue) {
    get().setOldValue(oldValue);
    return (HistoryEntryBuilder) this;
  }

  public Integer primaryKey() {
    return get().getPrimaryKey();
  }

  public HistoryEntryBuilder primaryKey(Integer primaryKey) {
    get().setPrimaryKey(primaryKey);
    return (HistoryEntryBuilder) this;
  }

  public HistoryEntryBuilder with(Integer primaryKey) {
    return primaryKey(primaryKey);
  }

  protected Integer defaultPrimaryKey() {
    return 0;
  }

  public String propertyName() {
    return get().getPropertyName();
  }

  public HistoryEntryBuilder propertyName(String propertyName) {
    get().setPropertyName(propertyName);
    return (HistoryEntryBuilder) this;
  }

  public String rootTableName() {
    return get().getRootTableName();
  }

  public HistoryEntryBuilder rootTableName(String rootTableName) {
    get().setRootTableName(rootTableName);
    return (HistoryEntryBuilder) this;
  }

  protected String defaultRootTableName() {
    return "rootTableName";
  }

  public String type() {
    return get().getType();
  }

  public HistoryEntryBuilder type(String type) {
    get().setType(type);
    return (HistoryEntryBuilder) this;
  }

  protected String defaultType() {
    return "type";
  }

  public TimePoint updateTime() {
    return get().getUpdateTime();
  }

  public HistoryEntryBuilder updateTime(TimePoint updateTime) {
    get().setUpdateTime(updateTime);
    return (HistoryEntryBuilder) this;
  }

  public HistoryEntryBuilder with(TimePoint updateTime) {
    return updateTime(updateTime);
  }

  protected TimePoint defaultUpdateTime() {
    return TimePoint.from(0);
  }

  public String updater() {
    return get().getUpdater();
  }

  public HistoryEntryBuilder updater(String updater) {
    get().setUpdater(updater);
    return (HistoryEntryBuilder) this;
  }

  public HistoryEntry get() {
    return (features.domain.HistoryEntry) super.get();
  }

  @Override
  public HistoryEntryBuilder ensureSaved() {
    doEnsureSaved();
    return (HistoryEntryBuilder) this;
  }

  @Override
  public HistoryEntryBuilder use(AbstractBuilder<?> builder) {
    return (HistoryEntryBuilder) super.use(builder);
  }

  @Override
  public void delete() {
    HistoryEntry.queries.delete(get());
  }

  public static void deleteAll() {
    List<Long> ids = HistoryEntry.queries.findAllIds();
    for (Long id : ids) {
      HistoryEntry.queries.delete(HistoryEntry.queries.find(id));
    }
  }

}
