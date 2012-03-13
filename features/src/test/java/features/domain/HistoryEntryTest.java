package features.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import joist.domain.uow.UoW;

import org.junit.Test;

public class HistoryEntryTest extends AbstractFeaturesTest {

  @Test
  public void testInsert() {
    if (!this.isHistoryImplemented()) {
      return;
    }

    Primitives p = new Primitives();
    p.setName("name");
    p.setFlag(true);
    this.commitAndReOpen();

    assertEntry(1, "insert", 1, "primitives", null, null, null, "testing");
  }

  @Test
  public void testUpdate() {
    if (!this.isHistoryImplemented()) {
      return;
    }

    Primitives p = new Primitives();
    p.setName("n1");
    p.setFlag(true);
    this.commitAndReOpen();

    p = this.reload(p);
    p.setName("N1");
    p.setFlag(false);
    this.commitAndReOpen();

    assertEntry(2, "update", 1, "primitives", "flag", "true", "false", "testing");
    assertEntry(3, "update", 1, "primitives", "name", "n1", "N1", "testing");
  }

  @Test
  public void testDelete() {
    if (!this.isHistoryImplemented()) {
      return;
    }

    Primitives p = new Primitives();
    p.setName("n1");
    p.setFlag(true);
    this.commitAndReOpen();

    p = this.reload(p);
    Primitives.queries.delete(p);
    this.commitAndReOpen();

    assertEntry(2, "delete", 1, "primitives", null, null, null, "testing");
  }

  @Test
  public void testInsertWithInheritance() {
    if (!this.isHistoryImplemented()) {
      return;
    }

    new InheritanceASubOne("s1", "ss1");
    this.commitAndReOpen();

    assertThat(HistoryEntry.queries.count(), is(1l));
    assertEntry(1, "insert", 1, "inheritance_a_base", null, null, null, "testing");
  }

  @Test
  public void testUpdateWithInheritance() {
    if (!this.isHistoryImplemented()) {
      return;
    }

    InheritanceASubOne s1 = new InheritanceASubOne("s1", "ss1");
    this.commitAndReOpen();

    s1 = this.reload(s1);
    s1.setName("s2");
    s1.setOne("ss2");
    this.commitAndReOpen();

    assertThat(HistoryEntry.queries.count(), is(3l));
    assertEntry(2, "update", 1, "inheritance_a_base", "one", "ss1", "ss2", "testing");
    assertEntry(3, "update", 1, "inheritance_a_base", "name", "s1", "s2", "testing");
  }

  @Test
  public void testDeleteWithInheritance() {
    if (!this.isHistoryImplemented()) {
      return;
    }

    InheritanceASubOne s1 = new InheritanceASubOne("s1", "ss1");
    this.commitAndReOpen();

    s1 = this.reload(s1);
    InheritanceASubOne.queries.delete(s1);
    this.commitAndReOpen();

    assertThat(HistoryEntry.queries.count(), is(2l));
    assertEntry(2, "delete", 1, "inheritance_a_base", null, null, null, "testing");
  }

  @Test
  public void testWithoutAnUpdater() {
    if (!this.isHistoryImplemented()) {
      return;
    }

    // unset the updater before calling commitAndReOpen
    UoW.setUpdater(null);
    this.commitAndReOpen();

    Primitives p = new Primitives();
    p.setName("name");
    p.setFlag(true);
    this.commitAndReOpen();

    assertEntry(1, "insert", 1, "primitives", null, null, null, null);
  }

  private boolean isHistoryImplemented() {
    return this.isMySql();
  }

  private static void assertEntry(
    int id,
    String type,
    int primaryKey,
    String rootTableName,
    String propertyName,
    String oldValue,
    String newValue,
    String updater) {
    HistoryEntry e = HistoryEntry.queries.find(id);
    assertThat(e.getType(), is(type));
    assertThat(e.getPrimaryKey(), is(primaryKey));
    assertThat(e.getRootTableName(), is(rootTableName));
    assertThat(e.getPropertyName(), is(propertyName));
    assertThat(e.getOldValue(), is(oldValue));
    assertThat(e.getNewValue(), is(newValue));
    assertThat(e.getUpdater(), is(updater));
  }

}
