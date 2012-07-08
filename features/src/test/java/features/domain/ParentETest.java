package features.domain;

import static features.domain.builders.Builders.aParentE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import joist.jdbc.JdbcException;

import org.junit.Test;

import features.domain.builders.ParentEBuilder;

public class ParentETest extends AbstractFeaturesTest {

  @Test
  public void selfReferenceDoesNotWorkInMySQL() {
    ParentEBuilder e = aParentE().name("e");
    e.parentE(e);
    try {
      this.commitAndReOpen();
      if (db.isMySQL()) {
        fail("MySQL should fail");
      }
    } catch (JdbcException je) {
      if (db.isPg()) {
        fail("PG should work");
      } else if (db.isMySQL()) {
        assertThat(je.getMessage().contains("foreign key constraint fails"), is(true));
      }
    }
  }

  @Test
  public void selfReferenceWorksAfterFlush() {
    ParentEBuilder e = aParentE().name("e");
    this.flush();
    e.parentE(e);
    this.commitAndReOpen();
    // deleting requires two steps
    e.get().setParentE(null);
    this.flush();
    ParentE.queries.delete(e.get());
    this.commitAndReOpen();
  }

}
