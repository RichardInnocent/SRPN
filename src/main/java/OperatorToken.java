/**
 * Abstract implementation of an operator token. This abstracts some of the complexities of how to
 * handle the stack in each instance, allowing the calculation implementation to be handled purely
 * by the {@link Operator}.
 */
public abstract class OperatorToken extends AbstractToken {

  /**
   * Gets the operator that is responsible for calculating the result.
   * @return The operator that is responsible for calculating the result.
   */
  public abstract Operator getOperator();

  @Override
  public void apply(SizeRestrictedStack<Double> operandStack) throws CalculatorException {
    /* If the stack has fewer than 2 elements, throw an exception and don't pop any values off or
     * attempt to calculate a value. */
    if (operandStack.size() < 2) {
      throw new StackUnderflowException();
    }

    /* Get the operands. Don't want to pop these off of the stack until the calculation has
     * completed successfully. */
    double operand1 = operandStack.get(operandStack.size() - 2);
    double operand2 = operandStack.get(operandStack.size() - 1);

    // Get the result - this will throw an exception if unsuccessful
    double result = getOperator().calculate(operand1, operand2);

    // Generating the result was successful so pop the operands off the stack and push on the result
    operandStack.pop();
    operandStack.pop();
    operandStack.push(result);
  }

  @Override
  public String getReadableValue() {
    return getOperator().getReadableName();
  }

  @Override
  public int hashCode() {
    return getOperator().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof OperatorToken)) {
      return false;
    }

    return getOperator().equals(((OperatorToken) o).getOperator());
  }

  /**
   * Determines if this operator token has a higher precedence than another.
   * @param otherOperator The other operator token to compare.
   * @return {@code true} if this token has a higher precedence than {@code otherOperator}, or
   * {@code false} if the precedence is lower or equal.
   */
  public boolean hasHigherPrecedenceThan(OperatorToken otherOperator) {
    return getOperator().compareTo(otherOperator.getOperator()) < 0;
  }

  /**
   * Determines if this operator token has a lower precedence than another.
   * @param otherOperator The other operator token to compare.
   * @return {@code true} if this token has a lower precedence than {@code otherOperator}, or
   * {@code false} if the precedence is higher or equal.
   */
  public boolean hasLowerPrecedenceThan(OperatorToken otherOperator) {
    return getOperator().compareTo(otherOperator.getOperator()) > 0;
  }

}
