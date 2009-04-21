package joist.util;

import java.math.RoundingMode;

import joist.util.AbstractFixedPrecision.BoundsException;
import junit.framework.Assert;
import junit.framework.TestCase;

public class FixedPrecisionTest extends TestCase {

    public static class Foo extends AbstractFixedPrecision<Foo> {
        private static final long serialVersionUID = 1L;

        private Foo(Initializer i) {
            super(i, 9, RoundingMode.HALF_EVEN);
        }

        @Override
        protected Foo newValue(Initializer i) {
            return new Foo(i);
        }

        public static Foo from(String value) {
            return new Foo(AbstractFixedPrecision.fromStringUtil(value));
        }

        public static Foo from(long value) {
            return new Foo(AbstractFixedPrecision.fromLongUtil(value));
        }

        public static Foo fromSerializedLong(long representation) {
            return new Foo(AbstractFixedPrecision.fromSerializedLongUtil(representation));
        }
    }

    public void testEquality() {
        Foo f1 = Foo.from("1.1");
        Foo f2 = Foo.from("1.1");
        Assert.assertNotSame(f1, f2);
        Assert.assertEquals(f1, f2);
    }

    public void testEqualityWithDifferentScales() {
        Foo f1 = Foo.from("1.1");
        Foo f2 = Foo.from("1.10");
        Assert.assertNotSame(f1, f2);
        Assert.assertEquals(f1, f2);
    }

    public void testSerializeAndDeserializeAreInverseOperations() {
        long serializedValue = 123456789;
        Assert.assertEquals(serializedValue, Foo.fromSerializedLong(serializedValue).toSerializedLong());
        // really--not lying about it (this operation is the other way around)
        Assert.assertEquals(Foo.from(serializedValue), this.performSerializationRoundTrip(Foo.from(serializedValue)));
        // but don't forget this is a hash function, injective though it is
        Assert.assertEquals(Foo.from("0.123456789"), Foo.fromSerializedLong(serializedValue));
    }

    public void testStringConstructionIsInherentlyUnsafeEvenIfRepresentable() {
        // Sanity check: representable precision should be unaffected by serialization round-trip
        Foo representablePrecision = Foo.from("4.123456789");
        this.assertNotSerializable(representablePrecision);
        this.assertSerializable(representablePrecision, representablePrecision.round());
    }

    public void testStringConstructionIsUnsafeWithExcessivePrecisionAsWell() {
        Foo excessivePrecision = Foo.from("4.1234567895");
        this.assertNotSerializable(excessivePrecision);
        this.assertNotSerializable(excessivePrecision.round(10)); // still unsafe
        this.assertSerializable(Foo.from("4.123456790"), excessivePrecision.round());
    }

    public void testAdditionIsUnsafeIfEitherValuesAreUnsafe() {
        Foo unsafeValue = Foo.from("4"); // strings are unsafe
        Foo safeValue = Foo.from(1);

        this.assertNotSerializable(unsafeValue.plus(safeValue));
        this.assertNotSerializable(safeValue.plus(unsafeValue));

        this.assertSerializable(Foo.from(5), unsafeValue.round().plus(safeValue));
        this.assertSerializable(Foo.from(5), safeValue.plus(unsafeValue.round()));
        this.assertSerializable(Foo.from(5), safeValue.plus(unsafeValue).round());
    }

    public void testDivisionIsAlwaysUnsafe() {
        Foo rationalResult = Foo.from(4).dividedBy(Foo.from(2));
        this.assertNotSerializable(rationalResult);
        this.assertSerializable(Foo.from(2), rationalResult.round());
    }

    public void testIrrationalWorksButIsUnsafeUntilRounded() {
        Foo irrationalResult = Foo.from(4).dividedBy(Foo.from(3)); // Result is 1.3333..
        Foo precisionLimitedResult = Foo.from("1.333333333"); // 9 decimal places is the limit

        // Unrounded extra 3's mean the unrounded value is larger
        Assert.assertTrue(irrationalResult.isGreaterThan(precisionLimitedResult));
        // Rounding extra 3's off means equality
        Assert.assertEquals(precisionLimitedResult, irrationalResult.round());

        // Serialization fails because of extra precision
        try {
            this.performSerializationRoundTrip(irrationalResult);
            Assert.fail();
        } catch (ArithmeticException ae) {
            // expected
        }

        // Rounding then serializing works
        Assert.assertEquals(precisionLimitedResult, this.performSerializationRoundTrip(irrationalResult.round()));
    }

    public void testIrrationalAdditionWorksButIsUnsafe() {
        Foo irrationalResult = Foo.from(4).dividedBy(Foo.from(3)); // Result is 1.3333..
        Foo unsafeResult = irrationalResult.plus(Foo.from("1.1"));
        this.assertNotSerializable(unsafeResult);
        this.assertSerializable(Foo.from("2.4"), unsafeResult.round(1));
    }

    public void testUpperBoundsEnforcement() {
        // The range of values that may be represented by this type is (-9223372036.854775808 to 9223372036.854775807)
        Foo maxValue = Foo.from("9223372036.854775807").round();
        // Sanity check: max representable value should be unaffected by serialization round-trip
        Assert.assertEquals(maxValue, this.performSerializationRoundTrip(maxValue));
        try {
            // ...and now for a wafer-thin mint
            maxValue.plus(Foo.from("0.0000000001"));
            Assert.fail("Bounds check failed to enforce.");
        } catch (BoundsException be) {
            // should happen
        }
    }

    public void testLowerBoundsEnforcement() {
        Foo minValue = Foo.from("-9223372036.854775808").round();
        // Sanity check: min representable value should be unaffected by serialization round-trip
        Assert.assertEquals(minValue, this.performSerializationRoundTrip(minValue));
        try {
            // ...and now for a wafer-thin mint
            minValue.minus(Foo.from("0.0000000001"));
            Assert.fail("Bounds check failed to enforce.");
        } catch (BoundsException be) {
            // should happen
        }
    }

    public void testFriendlyRendering() {
        // Normally, toString gives the full representation, with trailing zeros stripped 
        Foo value = Foo.from("1899.000006000");
        Assert.assertEquals("1899.000006", value.toString());

        // But display rounding is nice sometimes.

        // This will zero-extend the value (if necessary) to force the specified precision level
        Assert.assertEquals("1899.000006", value.toExplicitPrecisionString(7));

        // ...and will round to even if you stipulate a lower precision level than the internal representation
        Assert.assertEquals("1899.00001", value.toExplicitPrecisionString(5));
        Assert.assertEquals("1899", value.toExplicitPrecisionString(0));
    }

    public void testArithmeticOperations() {
        Assert.assertEquals(//
            Foo.from("0.00000000056789"),
            Foo.from("-12345.678901234").plus(Foo.from("12345.67890123456789"))); // excessive precision is not rounded during addition

        Assert.assertEquals(//
            Foo.from("0.00000000056789"),
            Foo.from("-12345.678901234").minus(Foo.from("-12345.67890123456789"))); // ...or subtraction

        Assert.assertEquals(//
            Foo.from("370350.0000037035"),
            Foo.from(12345).times(Foo.from("30.0000000003"))); // ...or with multiplication

        // Division, however, is rounded to 32-digit precision (round to even)
        // 30.0000000003/12345 =~ 0.002430133657375455650060753341433779
        Assert.assertEquals(//
            Foo.from("0.0024301336573754556500607533414338"),
            Foo.from("30.0000000003").dividedBy(Foo.from(12345)));

        // Notice that casting can be necessary if someone is doing weird crap using byte values (ie. something that would be affected by sign extension)
        // gee, thanks, Java. Integer.MAX_VALUE = 0x7fffffff; so, Foo.from(Integer.MAX_VALUE + 1) = -2147483648
        Assert.assertEquals(Foo.from(((long) Integer.MAX_VALUE) + 1), Foo.from(2).toThePower(31));

        // And rounding, as seen in the rendering test
        Assert.assertEquals(Foo.from("12345.68"), Foo.from("12345.675").round(2));
    }

    public void testLogicalOperations() {
        Assert.assertTrue(Foo.from("2.1").isGreaterThan(Foo.from("2.0")));
        Assert.assertTrue(Foo.from("2.0").isGreaterThan(Foo.from("-2.1")));
        Assert.assertFalse(Foo.from("2").isGreaterThan(Foo.from("2.0")));

        Assert.assertTrue(Foo.from("2.0").isGreaterThanOrEqualTo(Foo.from("-2")));
        Assert.assertTrue(Foo.from("-2.0").isGreaterThanOrEqualTo(Foo.from("-2.1")));
        Assert.assertTrue(Foo.from("-2.0").isGreaterThanOrEqualTo(Foo.from("-2.0")));

        Assert.assertTrue(Foo.from("2.0").isLessThan(Foo.from("2.1")));
        Assert.assertTrue(Foo.from("-2.1").isLessThan(Foo.from("2.0")));
        Assert.assertFalse(Foo.from("2").isLessThan(Foo.from("2.0")));

        Assert.assertTrue(Foo.from("-2.0").isLessThanOrEqualTo(Foo.from("2")));
        Assert.assertTrue(Foo.from("2.0").isLessThanOrEqualTo(Foo.from("2.1")));
        Assert.assertTrue(Foo.from("-2.0").isLessThanOrEqualTo(Foo.from("-2.0")));

        Assert.assertTrue(Foo.from(0).isZero());
        Assert.assertTrue(Foo.from("0").isZero());
        Assert.assertTrue(Foo.from("0.0000000").isZero());
    }

    public void testHelperMethods() {
        Assert.assertEquals(Foo.from("4.0002"), Foo.max(Foo.from("4.0002"), Foo.from("4.0001")));
        Assert.assertEquals(Foo.from("-4.0001"), Foo.max(Foo.from("-4.0002"), Foo.from("-4.0001")));

        Assert.assertEquals(Foo.from("4.0001"), Foo.min(Foo.from("4.0002"), Foo.from("4.0001")));
        Assert.assertEquals(Foo.from("-4.0002"), Foo.min(Foo.from("-4.0002"), Foo.from("-4.0001")));
    }

    // Serializes, then deserializes the value
    private Foo performSerializationRoundTrip(Foo victim) {
        return Foo.fromSerializedLong(victim.toSerializedLong());
    }

    private void assertSerializable(Foo expected, Foo victim) {
        victim.toSerializedLong();
        Assert.assertEquals(expected, this.performSerializationRoundTrip(victim));
    }

    private void assertNotSerializable(Foo victim) {
        try {
            victim.toSerializedLong();
            Assert.fail();
        } catch (ArithmeticException ae) {
            // expected
        }
    }

}
