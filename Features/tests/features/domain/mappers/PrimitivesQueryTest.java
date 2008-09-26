package features.domain.mappers;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.exigencecorp.domainobjects.orm.repos.sql.Selecter;
import org.exigencecorp.domainobjects.orm.repos.sql.Updater;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.queries.Update;
import org.exigencecorp.domainobjects.queries.Where;
import org.exigencecorp.util.Copy;

import features.domain.Primitives;

public class PrimitivesQueryTest extends TestCase {

    public void testFindForIdEqualsSql() {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(p.id.equals(1));
        Assert.assertEquals("SELECT p.id, p.flag, p.name, p.version\n FROM primitives p\n WHERE p.id = ?", this.toSql(q));
        Assert.assertEquals(Copy.list(1), this.toParameters(q));
    }

    public void testFindForNameEqualsSql() {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(p.name.equals("bob"));
        Assert.assertEquals("SELECT p.id, p.flag, p.name, p.version\n FROM primitives p\n WHERE p.name = ?", this.toSql(q));
        Assert.assertEquals(Copy.list("bob"), this.toParameters(q));
    }

    public void testFindForNameEqualsOrderByNameSql() {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(p.name.equals("bob"));
        q.orderBy(p.name.asc());
        Assert.assertEquals("SELECT p.id, p.flag, p.name, p.version\n FROM primitives p\n WHERE p.name = ?\n ORDER BY p.name", this.toSql(q));
        Assert.assertEquals(Copy.list("bob"), this.toParameters(q));
    }

    public void testFindForIdLessThanMoreThanSql() {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(Where.and(p.id.lessThan(10), p.id.moreThan(1)));
        Assert.assertEquals("SELECT p.id, p.flag, p.name, p.version\n FROM primitives p\n WHERE p.id < ?\n AND p.id > ?", this.toSql(q));
        Assert.assertEquals(Copy.list(10, 1), this.toParameters(q));
    }

    public void testFindForNameDtoSql() {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.select(p.name.as("name"));
        Assert.assertEquals("SELECT p.name as name\n FROM primitives p", this.toSql(q));
    }

    public void testUpdateSql() {
        List<Integer> ids = Copy.list(1, 2, 3);

        PrimitivesAlias p = new PrimitivesAlias("p");
        Update<Primitives> q = Update.into(p);
        q.set(p.flag.to(true));
        q.where(p.id.in(ids));
        Assert.assertEquals("UPDATE primitives SET\n flag = ? WHERE id in (1,2,3)", this.toSql(q));
        Assert.assertEquals(Copy.list(Boolean.TRUE), this.toParameters(q));
    }

    private String toSql(Select<Primitives> q) {
        return new Selecter<Primitives>(null, q).toSql();
    }

    private List<Object> toParameters(Select<Primitives> q) {
        return new Selecter<Primitives>(null, q).getParameters();
    }

    private String toSql(Update<Primitives> q) {
        return new Updater<Primitives>(null, q).toSql();
    }

    private List<Object> toParameters(Update<Primitives> q) {
        return new Updater<Primitives>(null, q).getParameters();
    }

}
