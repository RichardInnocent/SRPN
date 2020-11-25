public class MultiplicationOperator extends Operator {

  @Override
  public double apply(double operand1, double operand2) {
    return truncateToBounds(operand1 * operand2);
  }

  @Override
  public String getReadableName() {
    return "*";
  }
}