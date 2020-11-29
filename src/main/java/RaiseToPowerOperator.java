/**
 * Operator responsible for handling raise to power operations.
 */
public class RaiseToPowerOperator extends Operator {

  @Override
  public double calculate(double operand1, double operand2) {
    // If the second operand is less than 0, cancel the operation
    if (operand2 < 0d) {
      throw new NegativePowerException();
    }

    /* This will be Double.NEGATIVE_INFINITY or Double.POSITIVE_INFINITY if > Double.MAX_VALUE or
     * < Double.MIN_VALUE. Truncating the result to the bounds of an integer should be sufficient.
     */
    return truncateToBounds(Math.pow(operand1, operand2));
  }

  @Override
  public String getReadableName() {
    return "^";
  }

  @Override
  public int getPrecedence() {
    return OperatorPrecedence.RAISE_TO_POWER;
  }
}
