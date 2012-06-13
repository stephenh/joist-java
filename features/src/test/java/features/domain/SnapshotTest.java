package features.domain;

import static features.domain.builders.Builders.aChild;
import static features.domain.builders.Builders.aCodeADomainObject;
import static features.domain.builders.Builders.aParent;
import static features.domain.builders.Builders.aParentD;
import static features.domain.builders.Builders.aParentDChildA;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import joist.domain.uow.Block;
import joist.domain.uow.Snapshot;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;

import org.junit.Test;

public class SnapshotTest extends AbstractFeaturesTest {

  @Test
  public void testLoadCachedEntity() {
    aParent().name("p1");
    this.commitAndClose();
    // make a snapshot that has the parent loaded
    Snapshot snapshot = UoW.snapshot(repo, new Block() {
      public void go() {
        Parent.queries.find(1);
      }
    });
    Jdbc.resetStats();
    UoW.open(repo, null, snapshot);
    Parent p = UoW.load(Parent.class, 1l);
    assertThat(p.getId(), is(1l));
    assertThat(p.getName(), is("p1"));
    assertThat(p.getVersion(), is(0l));
    assertThat(Jdbc.numberOfQueries(), is(0));
  }

  @Test
  public void testLoadCachedEntitysUncachedChildren() {
    aChild().parent(aParent().name("p1")).name("c1");
    this.commitAndClose();
    // make a snapshot that has the parent loaded
    Snapshot snapshot = UoW.snapshot(repo, new Block() {
      public void go() {
        Parent.queries.find(1);
      }
    });
    Jdbc.resetStats();
    UoW.open(repo, null, snapshot);
    Parent p = UoW.load(Parent.class, 1l);
    assertThat(p.getName(), is("p1"));
    assertThat(p.getChilds().get(0).getName(), is("c1"));
    // child collection wasn't cached
    assertThat(Jdbc.numberOfQueries(), is(1));
  }

  @Test
  public void testLoadCachedEntitysCachedChildren() {
    aChild().parent(aParent().name("p1")).name("c1");
    this.commitAndClose();
    // make a snapshot that has the parent and children loaded
    Snapshot snapshot = UoW.snapshot(repo, new Block() {
      public void go() {
        Parent p = Parent.queries.find(1);
        p.getChilds();
      }
    });
    Jdbc.resetStats();
    UoW.open(repo, null, snapshot);
    Parent p = UoW.load(Parent.class, 1l);
    assertThat(p.getName(), is("p1"));
    assertThat(p.getChilds().get(0).getId(), is(1l));
    assertThat(p.getChilds().get(0).getName(), is("c1"));
    assertThat(p.getChilds().get(0).getVersion(), is(0l));
    assertThat(Jdbc.numberOfQueries(), is(0));
  }

  @Test
  public void testCachedEntitiesAreReadOnly() {
    aParent().name("p1");
    this.commitAndClose();
    // make a snapshot that has the parent loaded
    Snapshot snapshot = UoW.snapshot(repo, new Block() {
      public void go() {
        Parent.queries.find(1);
      }
    });
    UoW.open(repo, null, snapshot);
    Parent p = UoW.load(Parent.class, 1l);
    try {
      p.setName("foo");
      fail("Should have failed");
    } catch (Exception e) {
      assertThat(e.getMessage(), is("Cannot change Parent[1] as it was loaded from a snapshot"));
    }
  }

  @Test
  public void testCachedEntitiesCannotGetNewChildren() {
    // Otherwise very likely to get op-lock errors on the parent or stale data in other spawned snapshots
    aParent().name("p1");
    this.commitAndClose();
    // make a snapshot that has the parent loaded
    Snapshot snapshot = UoW.snapshot(repo, new Block() {
      public void go() {
        Parent.queries.find(1);
      }
    });
    UoW.open(repo, null, snapshot);
    Parent p = UoW.load(Parent.class, 1l);
    try {
      new Child(p, "c1");
      fail("Should have failed");
    } catch (Exception e) {
      assertThat(e.getMessage(), is("Cannot change Parent[1] as it was loaded from a snapshot"));
    }
  }

  @Test
  public void testCachedEntitiesCanGetNewChildrenIfTheCollectionIsSkipped() {
    aParentD().name("p1");
    this.commitAndClose();
    // make a snapshot that has the parent loaded
    Snapshot snapshot = UoW.snapshot(repo, new Block() {
      public void go() {
        ParentD.queries.find(1);
      }
    });
    UoW.open(repo, null, snapshot);
    ParentD p = UoW.load(ParentD.class, 1l);
    aParentDChildA().name("c1").parentD(p);
    this.commitAndReOpen();
    assertThat(ParentDChildA.queries.count(), is(1l));
  }

  @Test
  public void testCachedEntitiesHaveTheirEnumsCached() {
    aCodeADomainObject().defaults();
    this.commitAndClose();
    // make a snapshot that has the parent loaded
    Snapshot snapshot = UoW.snapshot(repo, new Block() {
      public void go() {
        CodeADomainObject.queries.find(1);
      }
    });
    Jdbc.resetStats();
    UoW.open(repo, null, snapshot);
    CodeADomainObject c = UoW.load(CodeADomainObject.class, 1l);
    assertThat(c.getCodeAColor(), is(CodeAColor.BLUE));
    assertThat(Jdbc.numberOfQueries(), is(0));
  }

  private void commitAndClose() {
    this.commitAndReOpen();
    UoW.close();
  }

}
