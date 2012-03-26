package features.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

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

}
