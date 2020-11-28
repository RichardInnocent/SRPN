/**
 * Responsible for calculating the multiplication of two numbers.
 */
public class MultiplicationOperator extends Operator {

  @Override
  public double calculate(double operand1, double operand2) {
    /* As long as operand1 and operand2 are within the bounds of an int, this should never overflow
     * a double. Therefore we can calculate the result as normal and then truncate to the bounds of
     * an int after. */
    return truncateToBounds(operand1 * operand2);
  }

  @Override
  public String getReadableName() {
    return "*";
  }

  @Override
  public int getPrecedence() {
    return OperatorPrecedence.MULTIPLICATION;
  }
}
