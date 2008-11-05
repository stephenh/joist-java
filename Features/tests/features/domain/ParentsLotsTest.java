package features.domain;

import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.util.Log;

public class ParentsLotsTest extends AbstractFeaturesTest {

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
        for (int i = 0; i < 5000; i++) {
            Parent p = UoW.getCurrent().getRepository().load(Parent.class, 2 + i);
            p.setName("foo" + i);
        }
        this.commitAndReOpen();
        Log.debug("Took {}ms", (System.currentTimeMillis() - start));
    }

}
