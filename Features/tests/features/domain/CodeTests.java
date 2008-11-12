package features.domain;

import junit.framework.Assert;

import org.exigencecorp.util.Log;

import features.domain.queries.Query;

public class CodeTests extends AbstractFeaturesTest {

    public void testSave() {
        CodeADomainObject d = new CodeADomainObject();
        d.setCodeAColor(CodeAColor.BLUE);
        d.setCodeASize(CodeASize.ONE);
        d.setName("foo");
        this.commitAndReOpen();

        Log.debug("Doing find");
        d = Query.codeADomainObject.find(2);
        Assert.assertEquals(CodeAColor.BLUE, d.getCodeAColor());
        Assert.assertEquals(CodeASize.ONE, d.getCodeASize());
    }

}
