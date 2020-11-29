/**
 * Most operands, <i>x</i> inserted by the user will likely be in the range
 * <pre><code>Integer.MIN_VALUE <= x <= Integer.MAX_VALUE</code></pre>
 * For these values, there's no need to worry about overflowing so these can safely be stored as
 * {@code int} values until they are applied to the stack. This {@link OperandToken} type should be
 * used for values that meet this criteria. For any values that lie outside of this range,
 * {@link BigOperandToken}s should be used instead.
 */
public final class SmallOperandToken extends OperandToken {

  private final int value;

  // Force the use of the static factory methods
  private SmallOperandToken(int value) {
    this.value = value;
  }

  @Override
  protected int intValue() {
    return value;
  }

  @Override
  public OperandToken flipSign() {
    if (value == Integer.MIN_VALUE) {
      /* This would flip to Integer.MAX_VALUE+1 so return this as a BigOperandToken instead so it
       * doesn't overflow. */
      return BigOperandToken.forValue(Integer.toString(value).substring(1), 10);
    }
    /* This is the common case - just multiply the value by minus 1 and return as a new token to
     * preserve immutability. */
    return new SmallOperandToken(-value);
  }

  /**
   * Creates a {@link SmallOperandToken} from the given value.
   * @param value The value of the token.
   * @return A token representing this value.
   */
  public static SmallOperandToken forValue(int value) {
    return new SmallOperandToken(value);
  }

  /**
   * Creates a {@link SmallOperandToken} from the given string.
   * @param value The numeric string representing this value.
   * @param radix The base with which to convert the value to an integer.
   * @return A token representing this value.
   * @throws NumberFormatException Thrown if {@code value} is not a valid integer in the given
   * radix.
   */
  public static SmallOperandToken forValue(String value, int radix) throws NumberFormatException {
    return forValue(Integer.parseInt(value, radix));
  }

  @Override
  public String getReadableValue() {
    return Integer.toString(value);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    // If the other object is a small integer, we can be a bit more efficient and compare the int
    // value itself. Otherwise, we'll have to use the parent method which allows the comparison
    // between various types of IntegerToken
    return other instanceof SmallOperandToken ?
        this.value == ((SmallOperandToken) other).value :
        super.equals(other);
  }

}
