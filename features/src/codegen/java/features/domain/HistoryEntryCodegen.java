package features.domain;

import com.domainlanguage.time.TimePoint;
import features.domain.queries.HistoryEntryQueries;
import joist.domain.AbstractChanged;
import joist.domain.AbstractDomainObject;
import joist.domain.Changed;
import joist.domain.Shim;
import joist.domain.uow.UoW;
import joist.domain.validation.rules.MaxLength;
import joist.domain.validation.rules.NotEmpty;
import joist.domain.validation.rules.NotNull;

@SuppressWarnings("all")
public abstract class HistoryEntryCodegen extends AbstractDomainObject {

  public static final HistoryEntryQueries queries;
  private Long id = null;
  private String newValue = null;
  private String oldValue = null;
  private Integer primaryKey = null;
  private String propertyName = null;
  private String rootTableName = null;
  private String type = null;
  private TimePoint updateTime = null;
  private String updater = null;
  private Long version = null;
  protected Changed changed;

  static {
    Aliases.historyEntry();
    queries = new HistoryEntryQueries();
  }

  protected HistoryEntryCodegen() {
    this.addExtraRules();
  }

  private void addExtraRules() {
    this.addRule(new MaxLength<HistoryEntry>(Shims.newValue, 255));
    this.addRule(new NotEmpty<HistoryEntry>(Shims.newValue));
    this.addRule(new MaxLength<HistoryEntry>(Shims.oldValue, 255));
    this.addRule(new NotEmpty<HistoryEntry>(Shims.oldValue));
    this.addRule(new NotNull<HistoryEntry>(Shims.primaryKey));
    this.addRule(new MaxLength<HistoryEntry>(Shims.propertyName, 255));
    this.addRule(new NotEmpty<HistoryEntry>(Shims.propertyName));
    this.addRule(new NotNull<HistoryEntry>(Shims.rootTableName));
    this.addRule(new MaxLength<HistoryEntry>(Shims.rootTableName, 100));
    this.addRule(new NotEmpty<HistoryEntry>(Shims.rootTableName));
    this.addRule(new NotNull<HistoryEntry>(Shims.type));
    this.addRule(new MaxLength<HistoryEntry>(Shims.type, 100));
    this.addRule(new NotEmpty<HistoryEntry>(Shims.type));
    this.addRule(new NotNull<HistoryEntry>(Shims.updateTime));
    this.addRule(new MaxLength<HistoryEntry>(Shims.updater, 100));
    this.addRule(new NotEmpty<HistoryEntry>(Shims.updater));
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    if (this.id != null) {
      throw new IllegalStateException(this + " id cannot be changed");
    }
    this.getChanged().record("id", this.id, id);
    this.id = id;
    if (UoW.isOpen()) {
      UoW.getIdentityMap().store(this);
    }
  }

  public String getNewValue() {
    return this.newValue;
  }

  public void setNewValue(String newValue) {
    this.getChanged().record("newValue", this.newValue, newValue);
    this.newValue = newValue;
  }

  protected void defaultNewValue(String newValue) {
    this.newValue = newValue;
  }

  public String getOldValue() {
    return this.oldValue;
  }

  public void setOldValue(String oldValue) {
    this.getChanged().record("oldValue", this.oldValue, oldValue);
    this.oldValue = oldValue;
  }

  protected void defaultOldValue(String oldValue) {
    this.oldValue = oldValue;
  }

  public Integer getPrimaryKey() {
    return this.primaryKey;
  }

  public void setPrimaryKey(Integer primaryKey) {
    this.getChanged().record("primaryKey", this.primaryKey, primaryKey);
    this.primaryKey = primaryKey;
  }

  protected void defaultPrimaryKey(Integer primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getPropertyName() {
    return this.propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.getChanged().record("propertyName", this.propertyName, propertyName);
    this.propertyName = propertyName;
  }

  protected void defaultPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public String getRootTableName() {
    return this.rootTableName;
  }

  public void setRootTableName(String rootTableName) {
    this.getChanged().record("rootTableName", this.rootTableName, rootTableName);
    this.rootTableName = rootTableName;
  }

  protected void defaultRootTableName(String rootTableName) {
    this.rootTableName = rootTableName;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.getChanged().record("type", this.type, type);
    this.type = type;
  }

  protected void defaultType(String type) {
    this.type = type;
  }

  public TimePoint getUpdateTime() {
    return this.updateTime;
  }

  public void setUpdateTime(TimePoint updateTime) {
    this.getChanged().record("updateTime", this.updateTime, updateTime);
    this.updateTime = updateTime;
  }

  protected void defaultUpdateTime(TimePoint updateTime) {
    this.updateTime = updateTime;
  }

  public String getUpdater() {
    return this.updater;
  }

  public void setUpdater(String updater) {
    this.getChanged().record("updater", this.updater, updater);
    this.updater = updater;
  }

  protected void defaultUpdater(String updater) {
    this.updater = updater;
  }

  public Long getVersion() {
    return this.version;
  }

  public HistoryEntryChanged getChanged() {
    if (this.changed == null) {
      this.changed = new HistoryEntryChanged((HistoryEntry) this);
    }
    return (HistoryEntryChanged) this.changed;
  }

  @Override
  public void clearAssociations() {
    super.clearAssociations();
  }

  static class Shims {
    protected static final Shim<HistoryEntry, Long> id = new Shim<HistoryEntry, Long>() {
      public void set(HistoryEntry instance, Long id) {
        ((HistoryEntryCodegen) instance).id = id;
      }
      public Long get(HistoryEntry instance) {
        return ((HistoryEntryCodegen) instance).id;
      }
      public String getName() {
        return "id";
      }
    };
    protected static final Shim<HistoryEntry, String> newValue = new Shim<HistoryEntry, String>() {
      public void set(HistoryEntry instance, String newValue) {
        ((HistoryEntryCodegen) instance).newValue = newValue;
      }
      public String get(HistoryEntry instance) {
        return ((HistoryEntryCodegen) instance).newValue;
      }
      public String getName() {
        return "newValue";
      }
    };
    protected static final Shim<HistoryEntry, String> oldValue = new Shim<HistoryEntry, String>() {
      public void set(HistoryEntry instance, String oldValue) {
        ((HistoryEntryCodegen) instance).oldValue = oldValue;
      }
      public String get(HistoryEntry instance) {
        return ((HistoryEntryCodegen) instance).oldValue;
      }
      public String getName() {
        return "oldValue";
      }
    };
    protected static final Shim<HistoryEntry, Integer> primaryKey = new Shim<HistoryEntry, Integer>() {
      public void set(HistoryEntry instance, Integer primaryKey) {
        ((HistoryEntryCodegen) instance).primaryKey = primaryKey;
      }
      public Integer get(HistoryEntry instance) {
        return ((HistoryEntryCodegen) instance).primaryKey;
      }
      public String getName() {
        return "primaryKey";
      }
    };
    protected static final Shim<HistoryEntry, String> propertyName = new Shim<HistoryEntry, String>() {
      public void set(HistoryEntry instance, String propertyName) {
        ((HistoryEntryCodegen) instance).propertyName = propertyName;
      }
      public String get(HistoryEntry instance) {
        return ((HistoryEntryCodegen) instance).propertyName;
      }
      public String getName() {
        return "propertyName";
      }
    };
    protected static final Shim<HistoryEntry, String> rootTableName = new Shim<HistoryEntry, String>() {
      public void set(HistoryEntry instance, String rootTableName) {
        ((HistoryEntryCodegen) instance).rootTableName = rootTableName;
      }
      public String get(HistoryEntry instance) {
        return ((HistoryEntryCodegen) instance).rootTableName;
      }
      public String getName() {
        return "rootTableName";
      }
    };
    protected static final Shim<HistoryEntry, String> type = new Shim<HistoryEntry, String>() {
      public void set(HistoryEntry instance, String type) {
        ((HistoryEntryCodegen) instance).type = type;
      }
      public String get(HistoryEntry instance) {
        return ((HistoryEntryCodegen) instance).type;
      }
      public String getName() {
        return "type";
      }
    };
    protected static final Shim<HistoryEntry, TimePoint> updateTime = new Shim<HistoryEntry, TimePoint>() {
      public void set(HistoryEntry instance, TimePoint updateTime) {
        ((HistoryEntryCodegen) instance).updateTime = updateTime;
      }
      public TimePoint get(HistoryEntry instance) {
        return ((HistoryEntryCodegen) instance).updateTime;
      }
      public String getName() {
        return "updateTime";
      }
    };
    protected static final Shim<HistoryEntry, String> updater = new Shim<HistoryEntry, String>() {
      public void set(HistoryEntry instance, String updater) {
        ((HistoryEntryCodegen) instance).updater = updater;
      }
      public String get(HistoryEntry instance) {
        return ((HistoryEntryCodegen) instance).updater;
      }
      public String getName() {
        return "updater";
      }
    };
    protected static final Shim<HistoryEntry, Long> version = new Shim<HistoryEntry, Long>() {
      public void set(HistoryEntry instance, Long version) {
        ((HistoryEntryCodegen) instance).version = version;
      }
      public Long get(HistoryEntry instance) {
        return ((HistoryEntryCodegen) instance).version;
      }
      public String getName() {
        return "version";
      }
    };
  }

  public static class HistoryEntryChanged extends AbstractChanged {
    public HistoryEntryChanged(HistoryEntry instance) {
      super(instance);
    }
    public boolean hasId() {
      return this.contains("id");
    }
    public Long getOriginalId() {
      return (Long) this.getOriginal("id");
    }
    public boolean hasNewValue() {
      return this.contains("newValue");
    }
    public String getOriginalNewValue() {
      return (java.lang.String) this.getOriginal("newValue");
    }
    public boolean hasOldValue() {
      return this.contains("oldValue");
    }
    public String getOriginalOldValue() {
      return (java.lang.String) this.getOriginal("oldValue");
    }
    public boolean hasPrimaryKey() {
      return this.contains("primaryKey");
    }
    public Integer getOriginalPrimaryKey() {
      return (java.lang.Integer) this.getOriginal("primaryKey");
    }
    public boolean hasPropertyName() {
      return this.contains("propertyName");
    }
    public String getOriginalPropertyName() {
      return (java.lang.String) this.getOriginal("propertyName");
    }
    public boolean hasRootTableName() {
      return this.contains("rootTableName");
    }
    public String getOriginalRootTableName() {
      return (java.lang.String) this.getOriginal("rootTableName");
    }
    public boolean hasType() {
      return this.contains("type");
    }
    public String getOriginalType() {
      return (java.lang.String) this.getOriginal("type");
    }
    public boolean hasUpdateTime() {
      return this.contains("updateTime");
    }
    public TimePoint getOriginalUpdateTime() {
      return (com.domainlanguage.time.TimePoint) this.getOriginal("updateTime");
    }
    public boolean hasUpdater() {
      return this.contains("updater");
    }
    public String getOriginalUpdater() {
      return (java.lang.String) this.getOriginal("updater");
    }
    public boolean hasVersion() {
      return this.contains("version");
    }
    public Long getOriginalVersion() {
      return (Long) this.getOriginal("version");
    }
  }

}
