package features.domain;

import static features.domain.builders.Builders.aChild;
import static features.domain.builders.Builders.aCodeADomainObject;
import static features.domain.builders.Builders.aManyToManyABar;
import static features.domain.builders.Builders.aManyToManyAFoo;
import static features.domain.builders.Builders.aParent;
import static features.domain.builders.Builders.aPrimitives;
import static features.domain.builders.Builders.existing;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import joist.domain.uow.UoW;

import org.junit.Assert;
import org.junit.Test;

import features.domain.builders.ChildBuilder;
import features.domain.builders.CodeADomainObjectBuilder;
import features.domain.builders.ManyToManyAFooBuilder;
import features.domain.builders.ParentBuilder;
import features.domain.builders.PrimitivesBuilder;

public class BuildersTest extends AbstractFeaturesTest {

  @Test
  public void testEquality() {
    Parent p = new Parent();
    ParentBuilder b1 = new ParentBuilder(p);
    ParentBuilder b2 = new ParentBuilder(p);
    Assert.assertEquals(b1, b2);
  }

  @Test
  public void testAddedFunctionalityToRegularMethod() {
    ChildBuilder c = aChild().parent(new Parent());
    assertThat(c.name(), is("foo"));
  }

  @Test
  public void testAddedFunctionalityToBuilderMethod() {
    ChildBuilder c = aChild().parent(aParent());
    assertThat(c.name(), is("foo"));
  }

  @Test
  public void testAddedFunctionalityToWithMethod() {
    ChildBuilder c = aChild().with(aParent());
    assertThat(c.name(), is("foo"));
  }

  @Test
  public void testDefaults() {
    PrimitivesBuilder p = aPrimitives().defaults();
    assertThat(p.get().getId(), is(nullValue()));
    assertThat(p.get().getVersion(), is(nullValue()));
    assertThat(p.name(), is("name"));
    assertThat(p.flag(), is(false));
  }

  @Test
  public void testOverrideDefaults() {
    ParentBuilder p = aParent().defaults();
    assertThat(p.name(), is("parent"));
  }

  @Test
  public void testDefaultsForCodes() {
    CodeADomainObject o = new CodeADomainObject();
    // the cstr sets one code, not the other
    assertThat(o.getCodeAColor(), is(CodeAColor.BLUE));
    assertThat(o.getCodeASize(), is(nullValue()));
    // but the defaults method sets both
    existing(o).defaults();
    assertThat(o.getCodeAColor(), is(CodeAColor.BLUE));
    assertThat(o.getCodeASize(), is(CodeASize.ONE));
  }

  @Test
  public void testDefaultsForEntities() {
    ChildBuilder c = aChild().defaults();
    assertThat(c.name(), is("foo")); // er, side-effect of parents call
    assertThat(c.parent(), is(not(nullValue())));
    assertThat(c.parent().name(), is("parent"));
  }

  @Test
  public void testFluentCodeValues() {
    CodeADomainObjectBuilder d = aCodeADomainObject().blue().one();
    assertThat(d.codeAColor(), is(CodeAColor.BLUE));
    assertThat(d.codeASize(), is(CodeASize.ONE));
    // also has fluent accessors
    assertThat(d.isBlue(), is(true));
    assertThat(d.isOne(), is(true));
  }

  @Test
  public void testIdAutoFlushes() {
    Long id = aParent().defaults().id();
    assertThat(id, is(1l));
  }

  @Test
  public void testGetIdDoesNotAutoFlush() {
    Long id = aParent().defaults().get().getId();
    assertThat(id, is(nullValue()));
  }

  @Test
  public void testManyToManyWithBuilder() {
    ManyToManyAFooBuilder f = aManyToManyAFoo();
    aManyToManyABar().with(f);
    aManyToManyABar().with(f.get());
    aManyToManyABar().manyToManyAFoo(f);
    aManyToManyABar().manyToManyAFoo(f.get());
    assertThat(f.manyToManyABars().size(), is(4));
    assertThat(f.manyToManyABar(0), is(not(nullValue())));
  }

  @Test
  public void testEnsureSavedWorksIfNothingModifiedYet() {
    try {
      aParent().ensureSaved();
      fail();
    } catch (RuntimeException re) {
      assertThat(re.getMessage(), is("New validation errors - Name is required - Parent[null]"));
    }
  }

  @Test
  public void testEnsureSavedFailsIfUoWIsClosed() {
    UoW.close();
    try {
      aParent().ensureSaved();
      fail();
    } catch (RuntimeException re) {
      assertThat(re.getMessage(), is("ensureSaved only works if the UoW is open"));
    }
  }

  @Test
  public void testEnsureSaved() {
    ParentBuilder p = aParent().defaults().ensureSaved();
    assertThat(p.get().getId(), is(1L));
  }

  @Test
  public void testDeleteANewObject() {
    ParentBuilder p = aParent().defaults();
    p.delete();
    this.commitAndReOpen();
    assertThat(Parent.queries.count(), is(0L));
  }

  @Test
  public void testDeleteAnExistingObject() {
    ParentBuilder p = aParent().defaults();
    this.commitAndReOpen();
    p.delete();
    this.commitAndReOpen();
    assertThat(Parent.queries.count(), is(0L));
  }

  @Test
  public void testDeleteAll() {
    aParent().defaults();
    aParent().defaults();
    this.commitAndReOpen();
    ParentBuilder.deleteAll();
    this.commitAndReOpen();
    assertThat(Parent.queries.count(), is(0L));
  }

}
