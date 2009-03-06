package org.exigencecorp.conversion;

import java.util.Calendar;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.exigencecorp.util.TestCounters;

public class ConverterTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        TestCounters.resetAll();
    }

    public void testIdentityWorks() {
        ConverterRegistry r = new ConverterRegistry();
        Integer i = new Integer(1);
        Assert.assertSame(i, r.convert(i, Integer.class));

        // Cached
        Assert.assertSame(i, r.convert(i, Integer.class));
        Assert.assertEquals(1, ConverterRegistry.probes.get());
    }

    public void testSubAsBaseWorksWithoutConversion() {
        ConverterRegistry r = new ConverterRegistry();
        Sub s = new Sub();
        Assert.assertSame(s, r.convert(s, Base.class));
        Assert.assertEquals(1, ConverterRegistry.probes.get());

        // Cached
        Assert.assertSame(s, r.convert(s, Base.class));
        Assert.assertEquals(1, ConverterRegistry.probes.get());
    }

    public void testBaseCannotGoToSub() {
        ConverterRegistry r = new ConverterRegistry();
        Base b = new Base();
        try {
            r.convert(b, Sub.class);
            Assert.fail();
        } catch (UnsupportedConversionException uce) {
            Assert.assertEquals(Sub.class, uce.getToType());
        }
    }

    public void testFailsIfUnsupported() {
        ConverterRegistry r = new ConverterRegistry();
        try {
            r.convert(1, Calendar.class);
            Assert.fail();
        } catch (UnsupportedConversionException uce) {
            Assert.assertEquals(Calendar.class, uce.getToType());
        }
    }

    public static class Base {
    }

    public static class Sub extends Base {
    }
}
