package joist.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * This class is an injective, non-surjective hash of a real, fixed-precision decimal number to a signed 64 bit integer.
 *
 * A PG SQL big int is the same as a Java long (signed 64 bit number, -2^63 to +2^63-1; -9223372036854775808 to +9223372036854775807)
 *
 * As we want the integer portion of this class to be capable of representing a signed 32-bit integer
 * (-2^31 to +2^31-l; -2147483648 to +2147483647), that means that at least 10 decimal digits must be
 * reserved for the integer portion.
 *
 * 2^63 =~ +9.22 * 10^18, consequently we have approximately 9 digits available for the decimal portion
 *
 * The range of values that may be represented by this type is (-9223372036.854775808 to +9223372036.854775807)
 */
public abstract class AbstractFixedPrecision<T extends AbstractFixedPrecision<T>> implements Comparable<T>, Serializable {

  private static final BigDecimal MAX_VALUE = AbstractFixedPrecision.fromSerializedLongUtilAsBigDecimal(Long.MAX_VALUE);
  private static final BigDecimal MIN_VALUE = AbstractFixedPrecision.fromSerializedLongUtilAsBigDecimal(Long.MIN_VALUE);
  private static final MathContext ZERO_NO_ROUND = new MathContext(0, RoundingMode.UNNECESSARY);
  private static final long serialVersionUID = 1;
  protected final BigDecimal value;
  private final boolean isSafe;
  private final int persistedScale;
  private final RoundingMode roundingMode;

  protected AbstractFixedPrecision(Initializer i, int persistedScale, RoundingMode roundingMode) {
    this.value = i.value;
    this.isSafe = i.isSafe;
    this.persistedScale = persistedScale;
    this.roundingMode = roundingMode;
    this.boundsCheck();
  }

  protected abstract T newValue(Initializer i);

  // Arithmetic operation methods

  public T plus(T addend) {
    return this.newValue(//
      new Initializer(this.value.add(addend.value, AbstractFixedPrecision.ZERO_NO_ROUND), this.isSafe && addend.isSafe));
  }

  public T minus(T subtrahend) {
    return this.newValue(//
      new Initializer(this.value.subtract(subtrahend.value, AbstractFixedPrecision.ZERO_NO_ROUND), this.isSafe && subtrahend.isSafe));
  }

  public T times(T multiplicand) {
    return this.newValue(//
      new Initializer(this.value.multiply(multiplicand.value, AbstractFixedPrecision.ZERO_NO_ROUND), this.isSafe && multiplicand.isSafe));
  }

  public T dividedBy(T divisor) {
    return this.newValue(//
      new Initializer(this.value.divide(divisor.value, new MathContext(32, this.roundingMode)), false));
  }

  public T toThePower(int power) {
    return this.newValue(new Initializer(this.value.pow(power, new MathContext(32, this.roundingMode)), false));
  }

  /** @return the value rounded to the persisted scale */
  public T round() {
    return this.round(this.persistedScale);
  }

  /** @return the value rounded to <code>decimalPlacesToWhichToRound</code>, safe if less than or equal to the persisted scale */
  public T round(int decimalPlacesToWhichToRound) {
    boolean isNowSafe = decimalPlacesToWhichToRound <= this.persistedScale;
    return this.newValue(new Initializer(this.value.setScale(decimalPlacesToWhichToRound, this.roundingMode), isNowSafe));
  }

  public String toExplicitPrecisionString(int decimalPlacesToDisplay) {
    return this.round(decimalPlacesToDisplay).value.toPlainString();
  }

  // Logical operation methods

  public boolean isGreaterThan(T other) {
    return this.compareTo(other) > 0;
  }

  public boolean isGreaterThanOrEqualTo(T other) {
    return this.compareTo(other) >= 0;
  }

  public boolean isLessThan(T other) {
    return this.compareTo(other) < 0;
  }

  public boolean isLessThanOrEqualTo(T other) {
    return this.compareTo(other) <= 0;
  }

  public boolean isZero() {
    return this.value.compareTo(BigDecimal.ZERO) == 0;
  }

  public boolean equals(Object other) {
    // BigDecimal.equals() compares value and scale, so 2 != 2.00. Fuck that.
    if (!(other instanceof AbstractFixedPrecision<?>)) {
      return false;
    }
    return this.value.compareTo(((T) other).value) == 0;
  }

  public int hashCode() {
    return this.value.hashCode();
  }

  public int compareTo(T other) {
    return this.value.compareTo(other.value);
  }

  public String toString() {
    String plain = this.value.toPlainString();
    if (plain.indexOf('.') > -1) {
      while (plain.endsWith("0")) {
        plain = plain.substring(0, plain.length() - 1);
      }
      if (plain.endsWith(".")) {
        plain = plain.substring(0, plain.length() - 1);
      }
    }
    return plain;
  }

  public static <T extends AbstractFixedPrecision<T>> T max(T a, T b) {
    if (a.isGreaterThan(b)) {
      return a;
    } else {
      return b;
    }
  }

  public static <T extends AbstractFixedPrecision<T>> T min(T a, T b) {
    if (a.isLessThan(b)) {
      return a;
    } else {
      return b;
    }
  }

  /** Prevent values that we cannot represent post-serialization. */
  private void boundsCheck() {
    if (this.value.compareTo(AbstractFixedPrecision.MAX_VALUE) > 0) {
      throw new BoundsException(this.value);
    }
    if (this.value.compareTo(AbstractFixedPrecision.MIN_VALUE) < 0) {
      throw new BoundsException(this.value);
    }
  }

  // Builder methods

  // Serialization methods

  /** @param value the string value, assumed to be unsafe */
  protected static Initializer fromStringUtil(final String _value) {
    if (_value == null) {
      return null;
    }
    String value = _value.replace(",", "");
    try {
      return new Initializer(new BigDecimal(value), false);
    } catch (NumberFormatException nfe) {
      throw new RuntimeException("Invalid number " + value, nfe);
    }
  }

  /** @param long the actual value (not serialized like {@link AbstractFixedPrecision#fromSerializedLongUtil}. */
  protected static Initializer fromLongUtil(long value) {
    return new Initializer(new BigDecimal(value), true);
  }

  /** @param double the actual value. */
  protected static Initializer fromDoubleUtil(double value) {
    return new Initializer(new BigDecimal(value), false);
  }

  /** @param value the actual value. */
  protected static Initializer fromBigDecimalUtil(BigDecimal value) {
    return new Initializer(value, false);
  }

  /** @param representation The serialized value, represented as actual value * 10^9 */
  protected static Initializer fromSerializedLongUtil(long representation) {
    return new Initializer(AbstractFixedPrecision.fromSerializedLongUtilAsBigDecimal(representation), true);
  }

  private static BigDecimal fromSerializedLongUtilAsBigDecimal(long representation) {
    return new BigDecimal(BigInteger.valueOf(representation), 9, new MathContext(19, RoundingMode.UNNECESSARY));
  }

  /** @return a serialized value, represented as value * 10^9--if over the precision limit, we fail */
  public long toSerializedLong() {
    if (!this.isSafe) {
      throw new ArithmeticException("An unsafe operation has been performed and explicit round() call is required.");
    }
    return this.value.movePointRight(9).longValueExact();
  }

  public static class BoundsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BoundsException(BigDecimal value) {
      super("Value " + value + " is outside the allowable representation");
    }
  }

  public static class Initializer {
    public final BigDecimal value;
    public final boolean isSafe;

    private Initializer(BigDecimal value, boolean isSafe) {
      this.value = value;
      this.isSafe = isSafe;
    }
  }

}
