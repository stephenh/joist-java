package joist.util;

import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Test;

public class FixedPrecisionLimitedTest {

  public static class Foo extends AbstractFixedPrecision<Foo> {
    private static final long serialVersionUID = 1L;

    private Foo(Initializer i) {
      super(i, 2, RoundingMode.HALF_UP);
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

  @Test
  public void testEquality() {
    Foo f1 = Foo.from("1.1");
    Foo f2 = Foo.from("1.1");
    Assert.assertNotSame(f1, f2);
    Assert.assertEquals(f1, f2);
  }

  @Test
  public void testEqualityWithDifferentScales() {
    Foo f1 = Foo.from("1.12");
    Foo f2 = Foo.from("1.120");
    Assert.assertNotSame(f1, f2);
    Assert.assertEquals(f1, f2);
  }

  @Test
  public void testRoundOfNineIsStillNotSafe() {
    Foo unsafe = Foo.from("1.0");
    this.assertNotSerializable(unsafe);
    this.assertNotSerializable(unsafe.round(9));
    this.assertSerializable(Foo.from(1), unsafe.round());
  }

  @Test
  public void testRoundIsHalfUp() {
    Foo unsafe = Foo.from("1.005");
    this.assertNotSerializable(unsafe);
    this.assertNotSerializable(unsafe.round(9));
    this.assertSerializable(Foo.from("1.01"), unsafe.round());
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
