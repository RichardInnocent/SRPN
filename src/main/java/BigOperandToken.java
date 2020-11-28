import java.math.BigInteger;

/**
 * <p>When inserting numbers on the SRPN, it's possible to insert very large numbers without the
 * calculator crashing. Instead, these are typically treated as {@link Integer#MAX_VALUE} or
 * {@link Integer#MIN_VALUE}. However, due to our tokenization process, typing something like
 * {@code -12345678901234567890} would actually resolve to two separate tokens:</p>
 *
 * <ul>
 *   <li>A {@link SubtractionToken} (for the "{@code -}")</li>
 *   <li>An integer token (for the "{@code 12345678901234567890}")</li>
 * </ul>
 *
 * <p>This is potentially problematic if we were to store everything as an integer while handling
 * saturation. For example, if we assumed that the operand token in the above example was to be
 * stored in a token using an {@code int} internally, we'd have to handle its overflow by setting
 * its value to {@link Integer#MAX_VALUE}. When we grouped that together with the
 * {@link SubtractionToken}, we'd end up with the number {@code -2147483647}. However, that's not
 * how the original SRPN would handle this case. Instead, the SRPN would interpret the minus sign
 * first, and report the value as {@code -2147483648} (i.e. {@link Integer#MIN_VALUE}).</p>
 *
 * <p>To avoid this issue, for any values larger than {@link Integer#MAX_VALUE}, we will instead
 * preserve their original value by storing them internally as a {@link BigInteger}. We can then
 * handle the overflowing later when we interact with the token.</p>
 *
 * <p>We could have used this token type to store all of our operands, but it's overkill for most
 * uses of the calculator.</p>
 */
public final class BigOperandToken extends OperandToken {

  // No need to initialise these each time
  private static final BigInteger MINUS_ONE = BigInteger.valueOf(-1L);
  private static final BigInteger UPPER_BOUND = BigInteger.valueOf(Integer.MAX_VALUE);
  private static final BigInteger LOWER_BOUND = BigInteger.valueOf(Integer.MIN_VALUE);

  // We have to keep the value as a BigInteger for now as it could be > Integer.MAX_VALUE or
  // < Integer.MIN_VALUE. When tokenizing, we can ignore this complexity until we get to later
  private final BigInteger value;

  // This exception should never be thrown if the tokenizer calls it appropriately
  private BigOperandToken(BigInteger value) throws NumberFormatException {
    this.value = value;
  }

  @Override
  protected int intValue() {
    if (value.compareTo(UPPER_BOUND) > 0) {
      // If the value is larger than Integer.MAX_VALUE, return Integer.MAX_VALUE
      return UPPER_BOUND.intValue();
    }
    if (value.compareTo(LOWER_BOUND) < 0) {
      // If the value is lower than Integer.MIN_VALUE, return Integer.MIN_VALUE
      return LOWER_BOUND.intValue();
    }

    // The value is in the range of an int so return its value directly
    return value.intValue();
  }

  /**
   * Creates a new BigIntegerToken with an absolute value equal to this one, with the sign flipped.
   * @return A new BigIntegerToken with the sign flipped.
   * @throws DummySegmentationFaultException Thrown if a minus sign is added and this makes the
   * value longer than 120 characters. This mirrors the behaviour of the calculator.
   */
  @Override
  public OperandToken flipSign() throws DummySegmentationFaultException {
    /* Keep the token itself immutable - instead return a new one where the value has been
     * multiplied by -1. We change it to a string so that we can assess its length and throw the
     * DummySegmentationFaultException if required. */
    return forValue(value.multiply(MINUS_ONE).toString(), 10);
  }

  /**
   * Transforms the given text into an integer token.
   * @param value The value to transform into an integer token.
   * @return The integer token.
   * @throws NumberFormatException Thrown if the value is not a number.
   * @throws DummySegmentationFaultException The calculator being emulated throws a segmentation
   * fault if the number being parsed is longer than 120 characters. This exception mirrors this
   * behaviour.
   */
  public static BigOperandToken forValue(String value, int radix)
      throws NumberFormatException, DummySegmentationFaultException {
    if (value.length() > 120) {
      /* If the value is longer than 120 characters in length (this includes the sign), then the
       * original SRPN throw a segmentation fault and exit. We can mirror that here by throwing an
       * uncaught exception. */
      throw new DummySegmentationFaultException();
    }

    BigInteger bigIntegerValue = new BigInteger(value, radix);

    // If less than 120 characters in length, we know it will fit in a BigInteger so this if safe
    return new BigOperandToken(bigIntegerValue);
  }

  @Override
  public String getReadableValue() {
    return value.toString();
  }
}
