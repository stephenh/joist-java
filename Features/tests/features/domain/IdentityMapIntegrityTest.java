package features.domain;

import junit.framework.Assert;

import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.uow.UoW;

public class IdentityMapIntegrityTest extends AbstractFeaturesTest {

    public void testInsertsGoIntoTheIdentityMap() {
        new Parent("p");
        this.flush();
        Assert.assertTrue(UoW.getIdentityMap().findOrNull(Parent.class, 2) != null);
    }

    public void testExplicitlySettingTheIdOfANewObject() {
        Parent p = new Parent("p");
        p.setId(new Id<Parent>(Parent.class, 10));
        this.commitAndReOpen();

        p = Parent.queries.find(10);
        Assert.assertEquals("p", p.getName());
    }

    public void testLoadIdThenAssignInUseIdIsCaughtByIdentityMap() {
        Parent p2 = new Parent("p2");
        this.commitAndReOpen();

        p2 = this.reload(p2);
        Parent pFake = new Parent("pFake");
        try {
            pFake.setId(new Id<Parent>(Parent.class, 2));
            Assert.fail();
        } catch (RuntimeException re) {
            Assert.assertEquals("Domain object conflicts with an existing id Parent[2]", re.getMessage());
        }
    }

    public void testAssignInUseIdThenLoadReturnsSameObjectSoIsNotCaughtUntilFlush() {
        Parent p2 = new Parent("p2");
        this.commitAndReOpen();

        // p2 = this.reload(p2);
        Parent pFake = new Parent("pFake");
        pFake.setId(new Id<Parent>(Parent.class, 2));
        p2 = this.reload(p2);
        Assert.assertSame(pFake, p2);

        try {
            this.commitAndReOpen();
            Assert.fail();
        } catch (RuntimeException re) {
            Assert.assertTrue(re.getMessage().contains("duplicate key"));
        }
    }

}
