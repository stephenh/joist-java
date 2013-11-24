package features.domain.builders;

import com.domainlanguage.time.TimePoint;
import features.domain.HistoryEntry;
import java.util.List;
import joist.domain.builders.AbstractBuilder;
import joist.domain.uow.UoW;

@SuppressWarnings("all")
public abstract class HistoryEntryBuilderCodegen extends AbstractBuilder<HistoryEntry> {

  public HistoryEntryBuilderCodegen(HistoryEntry instance) {
    super(instance);
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

  @Override
  public HistoryEntryBuilder defaults() {
    if (primaryKey() == null) {
      primaryKey(0);
    }
    if (rootTableName() == null) {
      rootTableName("rootTableName");
    }
    if (type() == null) {
      type("type");
    }
    if (updateTime() == null) {
      updateTime(TimePoint.from(0));
    }
    return (HistoryEntryBuilder) super.defaults();
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

  public String type() {
    return get().getType();
  }

  public HistoryEntryBuilder type(String type) {
    get().setType(type);
    return (HistoryEntryBuilder) this;
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
