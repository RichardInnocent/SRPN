import java.util.Objects;

/**
 * Represents an integer operand token. As the calculator should not overflow, we have to store the
 * value in its original form, even if this is larger than a primitive {@code int}. We can figure
 * out how to deal with this when computing the values.
 */
public abstract class OperandToken extends AbstractToken {

  @Override
  public void apply(SizeRestrictedStack<Double> operandStack) throws CalculatorException {
    operandStack.push((double) truncateDoubleToIntBounds());
  }

  /**
   * Gets the long value of the token. This result will be capped at the bounds of an integer, as
   * shown below:
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
  protected abstract int truncateDoubleToIntBounds();

  /**
   * Flips the sign of the number stored in the token.
   * @return A new integer token with an absolute value equal that this instance, but with the sign
   * flipped.
   */
  public abstract OperandToken flipSign();

  /**
   * Creates an integer token from the given string. Note that the result can either be a
   * {@link SmallOperandToken} or a {@link BigOperandToken}. The former is used if the original
   * value sits comfortably within a primitive {@code int} type. The latter is used when this is not
   * the case, but we want to preserve the original value that the user inputted.
   * @param value The integer represented as text.
   * @return An integer token.
   * @throws NumberFormatException Thrown if {@code value} is not a valid integer.
   * @throws DummySegmentationFaultException Thrown if {@code value.length() > 120} - this causes
   * the calculator to throw a segmentation fault so we should mirror this behaviour.
   */
  public static OperandToken forValue(String value)
      throws NumberFormatException, DummySegmentationFaultException {
    try {
      return SmallOperandToken.forValue(value);
    } catch (NumberFormatException e) {
      /* A NumberFormatException is thrown from the SmallIntegerToken.forValue(String) method if the
       * value is too large to be stored in an integer. If that exception is thrown, we should
       * instead try to store it in a BigInteger. */
      return BigOperandToken.forValue(value);
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
