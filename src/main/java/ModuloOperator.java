public class ModuloOperator extends Operator {

  @Override
  public double apply(double operand1, double operand2) throws DummyFloatingPointException {
    if (operand2 == 0) {
      throw new DummyFloatingPointException();
    }
    return operand1 % operand2; // TODO this can only be applied with an integer
  }

  @Override
  public String getReadableName() {
    return "%";
  }
}
