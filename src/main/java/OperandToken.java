import java.util.Objects;

/**
 * <p>Represents an integer operand token. While the operands are stored on the stack as floating point
 * numbers, at the point of insertion (and thus tokenization), they must be integers. Operand tokens
 * therefore store the inserted operand as an integer, ready to be added to the stack.</p>
 *
 * <p>As the calculator should not overflow, we have to store the value in its original form, even
 * if this is larger than a primitive {@code int}. We can figure out how to deal with this when
 * computing the values.</p>
 */
public abstract class OperandToken extends AbstractToken {

  @Override
  public void apply(SizeRestrictedStack<Double> operandStack) throws CalculatorException {
    operandStack.push((double) intValue());
  }

  /**
   * Gets the int value of the token. This result will be capped at the bounds of an integer,
   * saturating rather than overflowing. See the table below for examples.
   * <table>
   *   <tr>
   *     <th>Original value</th>
   *     <th>{@code truncatedIntValue}</th>
   *   </tr>
   *   <tr>
   *     <td>{@code 5}</td>
   *     <td>{@code 5}</td>
   *   </tr>
   *   <tr>
   *     <td>{@code -5}</td>
   *     <td>{@code -5}</td>
   *   </tr>
   *   <tr>
   *     <td>{@code Integer.MIN_VALUE-1}</td>
   *     <td>{@code Integer.MIN_VALUE}</td>
   *   </tr>
   *   <tr>
   *     <td>{@code Integer.MAX_VALUE+1}</td>
   *     <td>{@code Integer.MAX_VALUE}</td>
   *   </tr>
   * </table>
   */
  protected abstract int intValue();

  /**
   * Flips the sign of the number stored in the token.
   * @return A new integer token with an absolute value equal that this instance, but with the sign
   * flipped.
   */
  public abstract OperandToken flipSign();

  /**
   * <p>Creates an integer token from the given string. Note that the result can either be a
   * {@link SmallOperandToken} or a {@link BigOperandToken}. The former is used if the original
   * value sits comfortably within a primitive {@code int} type. The latter is used when this is not
   * the case, but we want to preserve the original value that the user inputted.</p>
   *
   * <p>Note that the input will be interpreted as octal if {@code value} starts with 0.</p>
   * @param value The integer represented as text.
   * @return An integer token.
   * @throws NumberFormatException Thrown if {@code value} is not a valid integer.
   * @throws DummySegmentationFaultException Thrown if {@code value.length() > 120} - this causes
   * the calculator to throw a segmentation fault so we should mirror this behaviour.
   */
  public static OperandToken forValue(String value)
      throws NumberFormatException, DummySegmentationFaultException {
    // Base 8 is used if the value starts with a 0, otherwise use base 10
    int radix = value.startsWith("0") ? 8 : 10;
    try {
      return SmallOperandToken.forValue(value, radix);
    } catch (NumberFormatException e) {
      /* A NumberFormatException is thrown from the SmallIntegerToken.forValue(String) method if the
       * value is too large to be stored in an integer. If that exception is thrown, we should
       * instead try to store it in a BigInteger. */
      return BigOperandToken.forValue(value, radix);
    }
  }

  /**
   * Creates an integer token from the given int.
   * @param value The value.
   * @return An integer token.
   */
  public static SmallOperandToken forValue(int value) {
    return SmallOperandToken.forValue(value);
  }

  @Override
  public int hashCode() {
    return getReadableValue().hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof OperandToken)) {
      return false;
    }

    OperandToken that = (OperandToken) other;

    // Equal if the text representation of the numbers is identical. Therefore, something like
    // BigIntegerToken.forValue("12345").equals(SmallIntegerToken.forValue("12345")
    // will return true, as these tokens should be processed in the same way
    return Objects.equals(getReadableValue(), that.getReadableValue());
  }

}
