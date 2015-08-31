package features.domain;

import java.util.ArrayList;
import java.util.List;
import joist.domain.DomainObject;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.JoinClause;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.IntAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;
import joist.domain.orm.queries.columns.StringAliasColumn;
import joist.domain.orm.queries.columns.TimePointAliasColumn;

public class HistoryEntryAlias extends Alias<HistoryEntry> {

  private final List<AliasColumn<HistoryEntry, ?, ?>> columns = new ArrayList<AliasColumn<HistoryEntry, ?, ?>>();
  public final IdAliasColumn<HistoryEntry> id = new IdAliasColumn<HistoryEntry>(this, "id", HistoryEntryCodegen.Shims.id);
  public final StringAliasColumn<HistoryEntry> newValue = new StringAliasColumn<HistoryEntry>(this, "new_value", HistoryEntryCodegen.Shims.newValue);
  public final StringAliasColumn<HistoryEntry> oldValue = new StringAliasColumn<HistoryEntry>(this, "old_value", HistoryEntryCodegen.Shims.oldValue);
  public final IntAliasColumn<HistoryEntry> primaryKey = new IntAliasColumn<HistoryEntry>(this, "primary_key", HistoryEntryCodegen.Shims.primaryKey);
  public final StringAliasColumn<HistoryEntry> propertyName = new StringAliasColumn<HistoryEntry>(this, "property_name", HistoryEntryCodegen.Shims.propertyName);
  public final StringAliasColumn<HistoryEntry> rootTableName = new StringAliasColumn<HistoryEntry>(this, "root_table_name", HistoryEntryCodegen.Shims.rootTableName);
  public final StringAliasColumn<HistoryEntry> type = new StringAliasColumn<HistoryEntry>(this, "type", HistoryEntryCodegen.Shims.type);
  public final TimePointAliasColumn<HistoryEntry> updateTime = new TimePointAliasColumn<HistoryEntry>(this, "update_time", HistoryEntryCodegen.Shims.updateTime);
  public final StringAliasColumn<HistoryEntry> updater = new StringAliasColumn<HistoryEntry>(this, "updater", HistoryEntryCodegen.Shims.updater);
  public final LongAliasColumn<HistoryEntry> version = new LongAliasColumn<HistoryEntry>(this, "version", HistoryEntryCodegen.Shims.version);

  public HistoryEntryAlias() {
    this("he0", null, true);
  }

  public HistoryEntryAlias(String alias) {
    this(alias, null, true);
  }

  public HistoryEntryAlias(String alias, Object noopBaseAlias, boolean addSubClasses) {
    super(HistoryEntry.class, "history_entry", alias);
    this.columns.add(this.id);
    this.columns.add(this.newValue);
    this.columns.add(this.oldValue);
    this.columns.add(this.primaryKey);
    this.columns.add(this.propertyName);
    this.columns.add(this.rootTableName);
    this.columns.add(this.type);
    this.columns.add(this.updateTime);
    this.columns.add(this.updater);
    this.columns.add(this.version);
  }

  public List<AliasColumn<HistoryEntry, ?, ?>> getColumns() {
    return this.columns;
  }

  public IdAliasColumn<HistoryEntry> getIdColumn() {
    return this.id;
  }

  public LongAliasColumn<HistoryEntry> getVersionColumn() {
    return this.version;
  }

  public IdAliasColumn<HistoryEntry> getSubClassIdColumn() {
    return null;
  }

  public int getOrder() {
    return 2;
  }

  public <T extends DomainObject> JoinClause<T, HistoryEntry> on(ForeignKeyAliasColumn<T, HistoryEntry> on) {
    return new JoinClause<T, HistoryEntry>("INNER JOIN", this, on);
  }

  public <T extends DomainObject> JoinClause<T, HistoryEntry> leftOn(ForeignKeyAliasColumn<T, HistoryEntry> on) {
    return new JoinClause<T, HistoryEntry>("LEFT OUTER JOIN", this, on);
  }

}
