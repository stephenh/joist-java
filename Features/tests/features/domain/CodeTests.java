package features.domain;

import junit.framework.Assert;
import features.domain.queries.Query;

public class CodeTests extends AbstractFeaturesTest {

    public void testSave() {
        CodeADomainObject d = new CodeADomainObject();
        d.setCodeAColor(CodeAColor.BLUE);
        d.setCodeASize(CodeASize.ONE);
        d.setName("foo");
        this.commitAndReOpen();

        d = Query.codeADomainObject.find(2);
        Assert.assertEquals(CodeAColor.BLUE, d.getCodeAColor());
        Assert.assertEquals(CodeASize.ONE, d.getCodeASize());
    }

}
