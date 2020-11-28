/**
 * Operator responsible for determining the modulus of one operand by another.
 */
public class ModuloOperator extends Operator {

  @Override
  public double calculate(double operand1, double operand2) throws DummyFloatingPointException {
    if (Math.abs(operand2) < 1d) {
      /* If operand2 is between -1 and 1, this would cause the legacy SRPN to crash with a floating
       * point exception */
      throw new DummyFloatingPointException();
    }
    // Evaluate the result and truncate to the bounds of an integer
    return truncateToBounds(operand1 % operand2);
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
