package org.exigencecorp.conversion;

import junit.framework.Assert;
import junit.framework.TestCase;

public class DefaultConvertersTest extends TestCase {

    private ConverterRegistry r;

    public void setUp() throws Exception {
        super.setUp();
        this.r = ConverterRegistry.newRegistryWithDefaultConverters();
    }

    public void testStringToInt() {
        Assert.assertEquals(new Integer(1), this.r.convert("1", Integer.class));
    }

    public void testObjectToString() {
        Assert.assertEquals("1", this.r.convert(new Integer(1), String.class));
    }

}
