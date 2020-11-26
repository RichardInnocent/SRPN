public class ModuloOperator extends Operator {

  @Override
  public double apply(double operand1, double operand2) throws DummyFloatingPointException {
    if (operand2 < 1d && operand2 > -1d) {
      throw new DummyFloatingPointException();
    }
    return operand1 % operand2;
  }

  @Override
  public String getReadableName() {
    return "%";
  }

  @Override
  public int getPrecedence() {
    return OperatorPrecedence.MODULO;
  }
}
