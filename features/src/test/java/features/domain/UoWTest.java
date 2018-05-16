package features.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import features.domain.builders.Builders;
import joist.domain.uow.BlockWithReturnAndSafety;
import joist.domain.uow.BlockWithSafety;
import joist.domain.uow.UoW;

public class UoWTest extends AbstractFeaturesTest {

  @Test
  public void blockWithSafetyUoWDoesNotCommit() {
    UoW.close();
    UoW.go(repo, null, new BlockWithSafety() {
      public void go() throws Exception {
        Builders.aParent().defaults();
        throw new IllegalStateException("failed");
      }

      public void stopped(Exception e) {
      }
    });
    UoW.open(repo, null);
    assertEquals(0L, Parent.queries.count());
  }

  @Test
  public void blockWithSafetyUoWIsClosedInStopped() {
    UoW.close();
    final Boolean[] wasOpen = { null };
    UoW.go(repo, null, new BlockWithSafety() {
      public void go() throws Exception {
        throw new IllegalStateException("failed");
      }

      public void stopped(Exception e) {
        wasOpen[0] = UoW.isOpen();
      }
    });
    Assert.assertFalse(wasOpen[0]);
  }

  @Test
  public void blockWithSafetyAndReturnUoWIsClosedInStopped() {
    UoW.close();
    final Boolean[] wasOpen = { null };
    UoW.go(repo, null, new BlockWithReturnAndSafety<Void>() {
      public Void go() {
        throw new IllegalStateException("failed");
      }

      public Void stopped(Exception e) {
        wasOpen[0] = UoW.isOpen();
        return null;
      }
    });
    Assert.assertFalse(wasOpen[0]);
  }
}
