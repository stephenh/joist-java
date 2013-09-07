package features.domain;

import static features.domain.builders.Builders.aInheritanceAOwner;
import static features.domain.builders.Builders.aInheritanceAThing;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import features.domain.builders.Builders;
import features.domain.builders.InheritanceAOwnerBuilder;
import features.domain.builders.InheritanceASubOneBuilder;
import features.domain.builders.InheritanceAThingBuilder;

public class InheritanceATest extends AbstractFeaturesTest {

  @Test
  public void testSaveInheritanceASubOne() {
    InheritanceASubOne a = new InheritanceASubOne();
    a.setName("name");
    a.setOne("one");
    this.commitAndReOpen();
    Assert.assertEquals(1, a.getId().intValue());

    a = this.reload(a);
    Assert.assertEquals("name", a.getName());
    Assert.assertEquals("one", a.getOne());
  }

  @Test
  public void testInsertInheritanceASubTwo() {
    InheritanceASubTwo b = new InheritanceASubTwo();
    b.setName("name");
    b.setTwo("two");
    this.commitAndReOpen();
    Assert.assertEquals(1, b.getId().intValue());

    b = this.reload(b);
    Assert.assertEquals("name", b.getName());
    Assert.assertEquals("two", b.getTwo());
  }

  @Test
  public void testUpdateInheritanceASubTwo() {
    InheritanceASubTwo b = new InheritanceASubTwo();
    b.setName("name");
    b.setTwo("two");
    this.commitAndReOpen();
    Assert.assertEquals(1, b.getId().intValue());

    b = this.reload(b);
    b.setName("name2");
    b.setTwo("twotwo");
    this.commitAndReOpen();

    b = this.reload(b);
    Assert.assertEquals("name2", b.getName());
    Assert.assertEquals("twotwo", b.getTwo());
  }

  @Test
  public void testQueryOnBaseClassReturnsSubClasses() {
    new InheritanceASubOne("namea", "a");
    new InheritanceASubTwo("nameb", "b");
    this.commitAndReOpen();

    List<InheritanceABase> l = InheritanceABase.queries.findAll();
    Assert.assertEquals(2, l.size());
    Assert.assertEquals(InheritanceASubOne.class, l.get(0).getClass());
    Assert.assertEquals(InheritanceASubTwo.class, l.get(1).getClass());
    Assert.assertEquals("a", ((InheritanceASubOne) l.get(0)).getOne());
    Assert.assertEquals("b", ((InheritanceASubTwo) l.get(1)).getTwo());

    InheritanceASubOne otherA = InheritanceASubOne.queries.find(1);
    Assert.assertTrue(otherA == l.get(0));
  }

  @Test
  public void testQueryOnSubClassReturnsOnlyThatSubClass() {
    new InheritanceASubOne("namea", "a");
    new InheritanceASubTwo("nameb", "b");
    this.commitAndReOpen();

    List<InheritanceASubOne> l = InheritanceASubOne.queries.findAll();
    Assert.assertEquals(1, l.size());
    Assert.assertEquals(InheritanceASubOne.class, l.get(0).getClass());

    InheritanceASubOne otherA = InheritanceASubOne.queries.find(1);
    Assert.assertTrue(otherA == l.get(0));

    InheritanceABase baseA = InheritanceABase.queries.find(1);
    Assert.assertTrue(otherA == baseA);
  }

  @Test
  public void testQueryOnSubClassByBaseClassAttribute() {
    new InheritanceASubOne("namea", "a");
    new InheritanceASubTwo("nameb", "b");
    this.commitAndReOpen();

    InheritanceASubOne a = InheritanceASubOne.queries.findByName("namea");
    Assert.assertEquals("a", a.getOne());

    InheritanceASubOne otherA = InheritanceASubOne.queries.find(1);
    Assert.assertTrue(otherA == a);
  }

  @Test
  public void testDelete() {
    InheritanceASubOne a = new InheritanceASubOne();
    a.setName("name");
    a.setOne("one");
    this.commitAndReOpen();

    InheritanceASubOne.queries.delete(a);
    this.commitAndReOpen();
  }

  @Test
  public void testLoadFromOwner() {
    InheritanceAOwnerBuilder owner = aInheritanceAOwner().name("owner");
    InheritanceAThingBuilder thing = aInheritanceAThing().name("thing");
    this.commitAndReOpen();

    InheritanceASubTwo a = new InheritanceASubTwo();
    a.setName("name");
    a.setTwo("two");
    a.setInheritanceAOwner(owner.get());
    a.setInheritanceAThing(thing.get());
    this.commitAndReOpen();

    InheritanceASubTwo a2 = (InheritanceASubTwo) owner.get().getInheritanceABases().get(0);
    Assert.assertEquals("name", a2.getName());
    Assert.assertEquals("two", a2.getTwo());
    Assert.assertEquals(1l, a2.getInheritanceAThing().getId().longValue());
  }

  @Test
  public void testBuilder() {
    // ensure name returns the right type
    InheritanceASubOneBuilder a = Builders.aInheritanceASubOne().name("foo");
    Assert.assertEquals("foo", a.name());

    InheritanceASubOneBuilder b = Builders.aInheritanceASubOne().inheritanceAOwner((InheritanceAOwner) null);
    Assert.assertEquals(null, b.inheritanceAOwner());

    InheritanceASubOneBuilder c = Builders.aInheritanceASubOne().inheritanceAOwner((InheritanceAOwnerBuilder) null);
    Assert.assertEquals(null, c.inheritanceAOwner());
  }

}
