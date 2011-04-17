package features.domain;

import joist.domain.orm.AliasRegistry;
import joist.domain.orm.queries.Select;
import joist.util.Interpolate;

public class ParentsLotsTest extends AbstractFeaturesTest {

  public void testNoOp() {
  }

  public void estLotsInLots() {
    long start = System.currentTimeMillis();
    for (int i = 0; i < 5000; i++) {
      Parent p = new Parent();
      p.setName("parent");
      this.commitAndReOpen();
    }
    println("Took {}ms", (System.currentTimeMillis() - start));
  }

  public void estLotsInOne() {
    long start = System.currentTimeMillis();
    for (int i = 0; i < 5000; i++) {
      Parent p = new Parent();
      p.setName("parent");
    }
    this.commitAndReOpen();
    println("Took {}ms", (System.currentTimeMillis() - start));
  }

  public void estLotsOfUpdateInOne() {
    long start = System.currentTimeMillis();
    for (int i = 0; i < 5000; i++) {
      Parent p = new Parent();
      p.setName("foo");
    }
    this.commitAndReOpen();
    long mid = System.currentTimeMillis();
    println("Insert took {}ms", (mid - start));
    for (int i = 0; i < 5000; i++) {
      Parent p = Parent.queries.find(2 + i);
      p.setName("foo" + i);
    }
    this.commitAndReOpen();
    long mid2 = System.currentTimeMillis();
    println("Update took {}ms", (mid2 - mid));
    for (int i = 0; i < 5000; i++) {
      // Query q = UoW.getCurrent().getRepository().getSession().createQuery("from Parent where name = :name");
      // q.setParameter("name", "foo" + i);
      // Parent p = (Parent) q.uniqueResult();
      ParentAlias a = (ParentAlias) AliasRegistry.get(Parent.class); // new ParentAlias("a");
      Select.from(a).where(a.name.eq("foo" + i)).unique();
    }
    long end = System.currentTimeMillis();
    println("Query took {}ms", (end - mid2));
    println("Took {}ms", (end - start));
  }

  private static void println(String s, Object... args) {
    System.out.println(Interpolate.string(s, args));
  }

}
