package features.domain;

import static features.domain.builders.Builders.aParentE;
import joist.jdbc.JdbcException;

import org.junit.Test;

import features.domain.builders.ParentEBuilder;

public class ParentETest extends AbstractFeaturesTest {

  @Test(expected = JdbcException.class)
  public void selfReferenceDoesNotWork() {
    ParentEBuilder e = aParentE().name("e");
    e.parentE(e);
    this.commitAndReOpen();
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
