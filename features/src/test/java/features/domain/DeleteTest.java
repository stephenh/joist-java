package features.domain;

import static features.domain.builders.Builders.aChild;
import static features.domain.builders.Builders.aParent;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;

import org.junit.Test;

import features.domain.builders.ChildBuilder;
import features.domain.builders.ParentBuilder;

public class DeleteTest extends AbstractFeaturesTest {

  @Test
  public void testDeleteEntityPointedAtByNullableForeignKey() {
    InheritanceAThing thing = new InheritanceAThing();
    thing.setName("name");

    // reuse InheritanceASubOne entity since it has a nullable fk
    InheritanceASubOne one = new InheritanceASubOne();
    one.setName("one");
    one.setOne("one");
    one.setInheritanceAThing(thing);

    this.commitAndReOpen();
    thing = this.reload(thing);
    one = this.reload(one);

    // now delete InheritanceAThing
    InheritanceAThing.queries.delete(thing);
    // clearAssocations worked
    assertThat(one.getInheritanceAThing(), is(nullValue()));
    // we need the SQL UPDATE to be issued first before the SQL DELETE
    this.commitAndReOpen();
    assertThat(InheritanceAThing.queries.count(), is(0l));
  }

  @Test
  public void testDeleteViaSqlCacade() {
    ChildBuilder c = aChild().defaults();
    long parentId = c.parent().id();
    this.commitAndReOpen();

    // this ensure the migration added a "ON DELETE CASCADE" to the child.parent_id column
    Jdbc.update(repo.getDataSource(), "DELETE FROM parent where id = ?", parentId);
    assertThat(Child.queries.count(), is(0l));
  }

  @Test
  public void testDeleteSkippedForNewObjects() {
    ParentBuilder p = aParent().defaults();
    UoW.delete(p.get());
    this.commitAndReOpen();

    assertThat(Parent.queries.count(), is(0l));
  }
}
