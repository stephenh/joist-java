package features.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class ListProxyTest {

  private Parent p = new Parent();
  private Child c = new Child();
  private Child c2 = new Child();

  @Test
  public void testSize() {
    this.p.addChild(this.c);
    assertEquals(1, this.p.getChilds().size());
  }

  @Test
  public void testIsEmpty() {
    assertEquals(true, this.p.getChilds().isEmpty());
    this.p.addChild(this.c);
    assertEquals(false, this.p.getChilds().isEmpty());
  }

  @Test
  public void testContains() {
    assertEquals(false, this.p.getChilds().contains(this.c));
    this.p.addChild(this.c);
    assertEquals(true, this.p.getChilds().contains(this.c));
  }

  @Test
  public void testIterator() {
    this.p.addChild(this.c);
    List<Child> seen = new ArrayList<Child>();
    for (Iterator<Child> i = this.p.getChilds().iterator(); i.hasNext();) {
      seen.add(i.next());
    }
    assertEquals(1, seen.size());
  }

  @Test
  public void testIteratorRemove() {
    this.p.addChild(this.c);
    for (Iterator<Child> i = this.p.getChilds().iterator(); i.hasNext();) {
      i.next();
      i.remove();
    }
    assertEquals(0, this.p.getChilds().size());
    assertEquals(null, this.c.getParent());
  }

  @Test
  public void testToArray() {
    this.p.addChild(this.c);
    Object[] a = this.p.getChilds().toArray();
    assertEquals(this.c, a[0]);
  }

  @Test
  public void testAdd() {
    this.p.getChilds().add(this.c);
    assertEquals(this.p, this.c.getParent());
  }

  @Test
  public void testAddBehavesLikeASet() {
    this.p.getChilds().add(this.c);
    this.p.getChilds().add(this.c);
    assertEquals(1, this.p.getChilds().size());
  }

  @Test
  public void testRemove() {
    this.p.addChild(this.c);
    assertEquals(this.p, this.c.getParent());
    this.p.getChilds().remove(this.c);
    assertEquals(null, this.c.getParent());
  }

  @Test
  public void testContainsAll() {
    this.p.addChild(this.c);
    List<Child> cs = new ArrayList<Child>();
    cs.add(this.c);
    assertEquals(true, this.p.getChilds().containsAll(cs));
    this.p.removeChild(this.c);
    assertEquals(false, this.p.getChilds().containsAll(cs));
  }

  @Test
  public void testRemoveAll() {
    this.p.addChild(this.c);
    List<Child> cs = new ArrayList<Child>();
    cs.add(this.c);
    this.p.getChilds().removeAll(cs);
    assertEquals(0, this.p.getChilds().size());
    assertEquals(null, this.c.getParent());
  }

  @Test
  public void testRetainAll() {
    this.p.addChild(this.c);
    this.p.addChild(this.c2);
    // empty list
    List<Child> cs = new ArrayList<Child>();
    this.p.getChilds().retainAll(cs);
    assertEquals(0, this.p.getChilds().size());
    assertEquals(null, this.c.getParent());
  }

  @Test
  public void testClear() {
    this.p.addChild(this.c);
    this.p.addChild(this.c2);
    assertEquals(2, this.p.getChilds().size());
    this.p.getChilds().clear();
    assertEquals(0, this.p.getChilds().size());
    assertEquals(null, this.c.getParent());
    assertEquals(null, this.c2.getParent());
  }

  @Test
  public void testGet() {
    this.p.addChild(this.c);
    assertEquals(this.c, this.p.getChilds().get(0));
  }

  @Test
  public void testRemoveIndex() {
    this.p.addChild(this.c);
    this.p.getChilds().remove(0);
    assertEquals(null, this.c.getParent());
  }

  @Test
  public void testIndexOf() {
    this.p.addChild(this.c);
    assertEquals(0, this.p.getChilds().indexOf(this.c));
    assertEquals(-1, this.p.getChilds().indexOf(this.c2));
  }

  @Test
  public void testLastIndexOf() {
    this.p.addChild(this.c);
    assertEquals(0, this.p.getChilds().lastIndexOf(this.c));
    assertEquals(-1, this.p.getChilds().lastIndexOf(this.c2));
  }

}
