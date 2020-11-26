public class RaiseToPowerOperator extends Operator {

  @Override
  public double apply(double operand1, double operand2) {
    if (operand2 < 0d) {
      throw new NegativePowerException();
    }

    /* Can't blindly use Math.pow here as the result could overflow. However,
     * Math.pow(Integer.MIN_INT, 2) won't overflow a long, although for any powers greater than
     * this, we can't be sure so we'll have to calculate it differently. */
    return operand2 < 3 ?
        raiseToPowerLessThan3(operand1, operand2) :
        raiseToPowerGreaterThanOrEqualTo3(operand1, operand2);
  }

  private double raiseToPowerLessThan3(double operand1, double operand2) {
    /* Should be safe to use Math.pow here as we're using integer operands so the result must be
     * <= abs(operand1) as this method should only be called if operand2 < 1. */
    return truncateToBounds(Math.pow(operand1, operand2));
  }

  private double raiseToPowerGreaterThanOrEqualTo3(double operand1, double operand2) {
    /* We've already handled the case where operand1 == 1 and 0, so abs(operand1) >= 2.
     * Therefore, if the power is greater than or equal to 31, we know that our result will overflow
     * We need this check here as even BigIntegers aren't capable of storing anything like
     * Integer.MAX_VALUE ^ Integer.MAX_VALUE so we'd get an arithmetic exception. */
    if (operand1 >= 2 && operand2 >= 31) {
        return Integer.MAX_VALUE;
    }

    // Should be safe to use doubles here as Integer.MAX_VALUE ^ 31 can be stored in a double
    // without overflowing
    return truncateToBounds(Math.pow(operand1, operand2));
  }

  @Override
  public String getReadableName() {
    return "^";
  }
}
