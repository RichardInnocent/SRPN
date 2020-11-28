import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Operator responsible for performing the one operand by another.
 */
public class DivisionOperator extends Operator {

  /**
   * Gets the result of {@code operand1/operand2}.
   * @param operand1 The numerator.
   * @param operand2 The denominator.
   * @return The result of {@code operand1/operand2}, truncated to the bounds of an integer.
   * @throws DivideByZeroException Thrown if {@code operand2} is {@code 0} (or within 1E-10).
   */
  @Override
  public double apply(double operand1, double operand2) throws DivideByZeroException {
    // Check that we're not dividing by 0
    verifyNotZero(operand2);

    /* If the division can result in a bigger number, we need to be conscious of overflowing so
     * perform the division using big decimals. Otherwise, we know it can't overflow as the result
     * will always be <= operand1 so use standard double division. */
    return Math.abs(operand2) < 1d ?
        performBigDecimalDivision(operand1, operand2) :
        performStandardDivision(operand1, operand2);
  }

  private void verifyNotZero(double value) throws DivideByZeroException {
    // Avoid using value == 0 here as floating point operations can be slightly inaccurate. This
    // should be a small enough increment to assess 0 (< 1/Integer.MIN_VALUE).
    if (Math.abs(value) < 1e-10d) {
      throw new DivideByZeroException();
    }
  }

  private double performStandardDivision(double operand1, double operand2) {
    /* Should only need to truncate to bounds if operand1 == Integer.MAX_VALUE and operand2 ~= -1,
     * but this is not a costly operation so let's do it anyway. */
    return truncateToBounds(operand1 / operand2);
  }

  private double performBigDecimalDivision(double operand1, double operand2) {
    BigDecimal result =
        new BigDecimal(operand1).divide(new BigDecimal(operand2), RoundingMode.HALF_UP);
    // Result of the division could be out of the bounds for an integer
    return truncateToBounds(result);
  }

  @Override
  public String getReadableName() {
    return "/";
  }

  @Override
  public int getPrecedence() {
    return OperatorPrecedence.DIVISION;
  }
}
