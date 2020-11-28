import java.math.BigDecimal;

/**
 * Abstract implementation of an operator. This provides the child implementations with some
 * valuable utility methods, as well as providing sensible {@link #toString()}, {@link #hashCode()}
 * and {@link #equals(Object)} implementations.
 */
public abstract class Operator implements Comparable<Operator> {

  // Static variables save reinitialising these constants multiple times
  private static final double MAX_VALUE = Integer.MAX_VALUE;
  private static final double MIN_VALUE = Integer.MIN_VALUE;
  private static final BigDecimal BIG_DECIMAL_MAX_VALUE = new BigDecimal(MAX_VALUE);
  private static final BigDecimal BIG_DECIMAL_MIN_VALUE = new BigDecimal(MIN_VALUE);

  /**
   * Calculates the result of the operator on the given operands.
   * @param operand1 The first operand.
   * @param operand2 The second operand.
   * @return The result of the operation. Implementations of this method should ensure that the
   * result is always truncated to the bounds of an integer. If the result is greater than
   * {@link Integer#MAX_VALUE}, the result should be reported as {@link Integer#MAX_VALUE}, and the
   * same logic should apply for results lower than {@link Integer#MIN_VALUE}.
   * @see #truncateToBounds(double) 
   * @see #truncateToBounds(BigDecimal)
   */
  public abstract double calculate(double operand1, double operand2);

  /**
   * Gets the readable name of the operator. This aids in debugging, and will be used by default as
   * the result of {@link #toString()}.
   * @return The readable name of the operator.
   */
  public abstract String getReadableName();

  /**
   * Checks if the value is greater than the maximum value permitted to be stored in the stack,
   * {@link Integer#MAX_VALUE}.
   * @param value The value to check.
   * @return {@code true} if the value is greater than {@link Integer#MAX_VALUE}.
   */
  protected final boolean isAboveBound(double value) {
    return value > MAX_VALUE;
  }

  /**
   * Checks if the value is lower than the minimum value permitted to be stored in the stack,
   * {@link Integer#MIN_VALUE}.
   * @param value The value to check.
   * @return {@code true} if the value is lower than {@link Integer#MIN_VALUE}.
   */
  protected final boolean isBelowBound(double value) {
    return value < MIN_VALUE;
  }

  /**
   * Takes an input and truncates it to the bounds of an integer if required. See the table for
   * examples.
   * <table>
   *   <tr>
   *     <th>Original value</th>
   *     <th>Returned value</th>
   *   </tr>
   *   <tr>
   *     <td>{@code value} such that {@code Integer.MIN_VALUE <= value <= Integer.MAX_VALUE}</td>
   *     <td>{@code value}</td>
   *   </tr>
   *   <tr>
   *     <td>{@code value} such that {@code value > Integer.MAX_VALUE}</td>
   *     <td>{@code Integer.MAX_VALUE}</td>
   *   </tr>
   *   <tr>
   *     <td>{@code value} such that {@code value < Integer.MIN_VALUE}</td>
   *     <td>{@code Integer.MIN_VALUE}</td>
   *   </tr>
   * </table>
   * @param value The value to truncate if required.
   * @return The truncate value.
   */
  protected final double truncateToBounds(double value) {
    if (isAboveBound(value)) {
      return MAX_VALUE;
    }
    if (isBelowBound(value)) {
      return MIN_VALUE;
    }
    return value;
  }

  /**
   * Checks if the value is greater than the maximum value permitted to be stored in the stack,
   * {@link Integer#MAX_VALUE}.
   * @param value The value to check.
   * @return {@code true} if the value is greater than {@link Integer#MAX_VALUE}.
   */
  protected final boolean isAboveBound(BigDecimal value) {
    return value.compareTo(BIG_DECIMAL_MAX_VALUE) > 0;
  }

  /**
   * Checks if the value is lower than the minimum value permitted to be stored in the stack,
   * {@link Integer#MIN_VALUE}.
   * @param value The value to check.
   * @return {@code true} if the value is lower than {@link Integer#MIN_VALUE}.
   */
  protected final boolean isBelowBound(BigDecimal value) {
    return value.compareTo(BIG_DECIMAL_MIN_VALUE) < 0;
  }

  /**
   * Takes an input and truncates it to the bounds of an integer if required. See the table for
   * examples.
   * <table>
   *   <tr>
   *     <th>Original value</th>
   *     <th>Returned value</th>
   *   </tr>
   *   <tr>
   *     <td>{@code value} such that {@code Integer.MIN_VALUE <= value <= Integer.MAX_VALUE}</td>
   *     <td>{@code value}</td>
   *   </tr>
   *   <tr>
   *     <td>{@code value} such that {@code value > Integer.MAX_VALUE}</td>
   *     <td>{@code Integer.MAX_VALUE}</td>
   *   </tr>
   *   <tr>
   *     <td>{@code value} such that {@code value < Integer.MIN_VALUE}</td>
   *     <td>{@code Integer.MIN_VALUE}</td>
   *   </tr>
   * </table>
   * @param value The value to truncate if required.
   * @return The truncate value.
   */
  protected final double truncateToBounds(BigDecimal value) {
    if (isAboveBound(value)) {
      return MAX_VALUE;
    }
    if (isBelowBound(value)) {
      return MIN_VALUE;
    }
    return value.intValue();
  }

  @Override
  public int hashCode() {
    return getReadableName().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    return o instanceof Operator &&
        getReadableName().equals(((Operator) o).getReadableName());
  }

  /**
   * Gets the precedence of the operator. Operators with higher precedence are executed before
   * operators with a lower precedence.
   * @return The precedence of the operator.
   */
  public abstract int getPrecedence();

  /**
   * Compares operators by their precedence. Operators with higher precedence will should be sorted
   * first.
   * @param other The other operator to compare.
   * @return The result of the comparison.
   * @see Comparable
   */
  @Override
  public int compareTo(Operator other) {
    // Precedence is in reverse - higher precedence means that it should appear first
    return Integer.compare(other.getPrecedence(), getPrecedence());
  }
}
