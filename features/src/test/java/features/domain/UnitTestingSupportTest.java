package features.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import joist.domain.orm.UnitTesting;
import joist.domain.uow.UoW;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnitTestingSupportTest {

  @Before
  public void enableUnitTesting() {
    UnitTesting.setEnabled(true);
  }

  @After
  public void disableUnitTesting() {
    UnitTesting.setEnabled(false);
  }

  @Test
  public void testLoadOneToMany() {
    assertThat(UoW.isOpen(), is(false));
    Parent p = new Parent();
    p.setId(1l);
    // pretend flushed in a stub repo
    p.getChanged().clear();
    assertThat(p.getChilds().size(), is(0));
  }

  @Test
  public void testLoadManyToOne() {
    assertThat(UoW.isOpen(), is(false));
    Child c = new Child();
    c.setId(1l);
    // pretend flushed in a stub repo
    c.getChanged().clear();
    assertThat(c.getParent(), is(nullValue()));
  }
}
