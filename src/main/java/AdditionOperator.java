/**
 * Operator responsible for adding two values together.
 */
public class AdditionOperator extends Operator {

  /**
   * Adds the two operands together and returns the result.
   * @param operand1 The first operand.
   * @param operand2 The second operand.
   * @return The result of the addition. If {@code operand1 + operand2} would overflow or underflow,
   * {@link Integer#MAX_VALUE} or {@link Integer#MIN_VALUE} respectively will be returned instead.
   */
  @Override
  public int apply(int operand1, int operand2) {
    /* Cast to longs before adding in case of an overflow, then truncate the result before casting
     * back to an int if required. */
    return truncateToBounds(((long) operand1) + ((long) operand2));
  }

  @Override
  public String getReadableName() {
    return "+";
  }
}
