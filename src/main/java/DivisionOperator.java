public class DivisionOperator extends Operator {

  @Override
  public double apply(double operand1, double operand2) {
    if (operand2 == 0) {
      throw new DivideByZeroException();
    }
    return truncateToBounds(operand1 / operand2); // TODO test operand2 < 1
  }

  @Override
  public String getReadableName() {
    return "/";
  }
}
