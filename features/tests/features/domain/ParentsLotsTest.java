package features.domain;

import joist.domain.orm.AliasRegistry;
import joist.domain.orm.queries.Select;
import joist.util.Log;

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
        Log.debug("Took {}ms", (System.currentTimeMillis() - start));
    }

    public void estLotsInOne() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Parent p = new Parent();
            p.setName("parent");
        }
        this.commitAndReOpen();
        Log.debug("Took {}ms", (System.currentTimeMillis() - start));
    }

    public void estLotsOfUpdateInOne() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Parent p = new Parent();
            p.setName("foo");
        }
        this.commitAndReOpen();
        long mid = System.currentTimeMillis();
        Log.debug("Insert took {}ms", (mid - start));
        for (int i = 0; i < 5000; i++) {
            Parent p = Parent.queries.find(2 + i);
            p.setName("foo" + i);
        }
        this.commitAndReOpen();
        long mid2 = System.currentTimeMillis();
        Log.debug("Update took {}ms", (mid2 - mid));
        for (int i = 0; i < 5000; i++) {
            // Query q = UoW.getCurrent().getRepository().getSession().createQuery("from Parent where name = :name");
            // q.setParameter("name", "foo" + i);
            // Parent p = (Parent) q.uniqueResult();
            ParentAlias a = (ParentAlias) AliasRegistry.get(Parent.class); // new ParentAlias("a");
            Select.from(a).where(a.name.equals("foo" + i)).unique();
        }
        long end = System.currentTimeMillis();
        Log.debug("Query took {}ms", (end - mid2));
        Log.debug("Took {}ms", (end - start));
    }

}
