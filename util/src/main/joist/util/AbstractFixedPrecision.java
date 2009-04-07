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

    private static final BigDecimal MAX_VALUE = AbstractFixedPrecision.fromSerializedLongUtil(Long.MAX_VALUE);
    private static final BigDecimal MIN_VALUE = AbstractFixedPrecision.fromSerializedLongUtil(Long.MIN_VALUE);
    private static final MathContext ZERO_NO_ROUND = new MathContext(0, RoundingMode.UNNECESSARY);
    private static final MathContext THIRTY_TWO_HALF_EVEN = new MathContext(32, RoundingMode.HALF_EVEN);
    private static final long serialVersionUID = 1;
    protected final BigDecimal value;

    protected AbstractFixedPrecision(BigDecimal value) {
        this.value = value;
        this.boundsCheck();
    }

    protected abstract T newValue(BigDecimal value);

    // Arithmetic operation methods

    public T plus(T addend) {
        return this.newValue(this.value.add(addend.value, AbstractFixedPrecision.ZERO_NO_ROUND));
    }

    public T minus(T subtrahend) {
        return this.newValue(this.value.subtract(subtrahend.value, AbstractFixedPrecision.ZERO_NO_ROUND));
    }

    public T times(T multiplicand) {
        return this.newValue(this.value.multiply(multiplicand.value, AbstractFixedPrecision.ZERO_NO_ROUND));
    }

    public T dividedBy(T divisor) {
        return this.newValue(this.value.divide(divisor.value, AbstractFixedPrecision.THIRTY_TWO_HALF_EVEN));
    }

    public T toThePower(int power) {
        return this.newValue(this.value.pow(power, AbstractFixedPrecision.THIRTY_TWO_HALF_EVEN));
    }

    public T round(int decimalPlacesToWhichToRound) {
        return this.newValue(this.value.setScale(decimalPlacesToWhichToRound, RoundingMode.HALF_EVEN));
    }

    public String toExplicitPrecisionString(int decimalPlacesToDisplay) {
        return this.round(decimalPlacesToDisplay).toString();
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
        if (!(other instanceof AbstractFixedPrecision)) {
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
        return this.value.toPlainString();
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

    private void boundsCheck() {
        // Prevent values that we cannot represent post-serialization
        if (this.value.compareTo(AbstractFixedPrecision.MAX_VALUE) > 0) {
            throw new RuntimeException("Value " + this.value + " larger than allowable representation");
        }
        if (this.value.compareTo(AbstractFixedPrecision.MIN_VALUE) < 0) {
            throw new RuntimeException("Value " + this.value + " smaller than allowable representation");
        }
    }

    // Builder methods

    // Serialization methods

    protected static BigDecimal fromStringUtil(String value) {
        return new BigDecimal(value, AbstractFixedPrecision.THIRTY_TWO_HALF_EVEN);
    }

    protected static BigDecimal fromLongUtil(long value) {
        return new BigDecimal(value);
    }

    /** @param representation The serialized value, represented as value * 10^9 */
    protected static BigDecimal fromSerializedLongUtil(long representation) {
        return new BigDecimal(BigInteger.valueOf(representation), 9, new MathContext(19, RoundingMode.UNNECESSARY));
    }

    public long toSerializedLong() {
        // Generate a serialized value, represented as (value rounded to precision limit) * 10^9
        BigDecimal scaledRepresentation = this.round(9).value.movePointRight(9);
        try {
            return scaledRepresentation.longValueExact();
        } catch (ArithmeticException ae) {
            throw new RuntimeException("Serialization error; internal value misrepresented. This should never happen. Raw value: " + this.value, ae);
        }
    }

}
