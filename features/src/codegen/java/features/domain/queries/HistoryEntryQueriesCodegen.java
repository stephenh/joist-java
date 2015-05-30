package features.domain.queries;

import features.domain.HistoryEntry;
import joist.domain.AbstractQueries;

abstract class HistoryEntryQueriesCodegen extends AbstractQueries<HistoryEntry> {

  public HistoryEntryQueriesCodegen() {
    super(HistoryEntry.class);
  }

  public void delete(HistoryEntry instance) {
    super.delete(instance);
  }

}
