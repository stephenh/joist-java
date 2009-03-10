package features.domain;

import java.util.List;

import junit.framework.Assert;

import org.exigencecorp.domainobjects.uow.UoW;

public class PagingATest extends AbstractFeaturesTest {

    public void testPageWithExplicitLimitAndOffset() {
        for (int i = 0; i < 10; i++) {
            new Parent("parent" + i);
        }
        this.commitAndReOpen();
        Assert.assertEquals(0, UoW.getIdentityMap().size());

        List<Parent> firstPage = Parent.queries.all(0, 2);
        Assert.assertEquals(2, firstPage.size());
        Assert.assertEquals(2, UoW.getIdentityMap().size());
        Assert.assertEquals("parent0", firstPage.get(0).getName());
        Assert.assertEquals("parent1", firstPage.get(1).getName());
        this.commitAndReOpen();

        List<Parent> secondPage = Parent.queries.all(2, 2);
        Assert.assertEquals(2, secondPage.size());
        Assert.assertEquals(2, UoW.getIdentityMap().size());
        Assert.assertEquals("parent2", secondPage.get(0).getName());
        Assert.assertEquals("parent3", secondPage.get(1).getName());
    }

    public void testPageWithImplicitSubList() {
        for (int i = 0; i < 10; i++) {
            new Parent("parent" + i);
        }
        this.commitAndReOpen();
        Assert.assertEquals(0, UoW.getIdentityMap().size());

        List<Parent> paged = Parent.queries.allPaged();
        Assert.assertEquals(10, paged.size());
        List<Parent> firstPage = paged.subList(0, 2);
        Assert.assertEquals(2, firstPage.size());
        Assert.assertEquals(2, UoW.getIdentityMap().size());
        Assert.assertEquals("parent0", firstPage.get(0).getName());
        Assert.assertEquals("parent1", firstPage.get(1).getName());
        this.commitAndReOpen();

        paged = Parent.queries.allPaged();
        Assert.assertEquals(10, paged.size());
        List<Parent> secondPage = paged.subList(2, 4);
        Assert.assertEquals(2, secondPage.size());
        Assert.assertEquals(2, UoW.getIdentityMap().size());
        Assert.assertEquals("parent2", secondPage.get(0).getName());
        Assert.assertEquals("parent3", secondPage.get(1).getName());
    }

}
