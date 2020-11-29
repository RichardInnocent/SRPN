/**
 * Operator to handle the subtraction of one operand from another.
 */
public class SubtractionOperator extends Operator {

  @Override
  public double calculate(double operand1, double operand2) {
    // Perform the calculation and then make sure that it's still within the bounds on an integer
    return truncateToBounds(operand1 - operand2);
  }

  @Override
  public String getReadableName() {
    return "-";
  }

  @Override
  public int getPrecedence() {
    return OperatorPrecedence.SUBTRACTION;
  }
}
