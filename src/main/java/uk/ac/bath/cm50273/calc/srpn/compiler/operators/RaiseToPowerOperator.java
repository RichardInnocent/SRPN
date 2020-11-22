package uk.ac.bath.cm50273.calc.srpn.compiler.operators;

import java.math.BigInteger;

public class RaiseToPowerOperator extends Operator {

  @Override
  public int apply(int operand1, int operand2) {
    if (operand1 == 0 || operand1 == 1) {
      return operand1;
    }

    if (operand1 == -1) {
      return operand2 % 2 == 0 ? 1 : -1;
    }

    if (operand2 == 1) {
      return operand1;
    }

    /* Can't blindly use Math.pow here as the result could overflow. However,
     * Math.pow(Integer.MIN_INT, 2) won't overflow a long, although for any powers greater than
     * this, we can't be sure so we'll have to calculate it differently. */
    return operand2 < 3 ?
        raiseToPowerLessThan3(operand1, operand2) :
        raiseToPowerGreaterThanOrEqualTo2(operand1, operand2);
  }

  private int raiseToPowerLessThan3(long operand1, long operand2) {
    /* Should be safe to use Math.pow here as we're using integer operands so the result must be
     * <= abs(operand1) as this method should only be called if operand2 < 1. */
    return truncateToBounds((long) Math.pow(operand1, operand2));
  }

  private int raiseToPowerGreaterThanOrEqualTo2(long operand1, long operand2) {
    /* We've already handled the case where operand1 == 1 and 0, so abs(operand1) >= 2.
     * Therefore, if the power is greater than or equal to 31, we know that our result will overflow
     * We need this check here as even BigIntegers aren't capable of storing anything like
     * Integer.MAX_VALUE ^ Integer.MAX_VALUE so we'd get an arithmetic exception. */
    if (operand2 >= 31) {
      if (operand1 > 0L) {
        return Integer.MAX_VALUE;
      } else {
        return operand2 % 2 == 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
      }
    }

    // Use BigInteger as we know it's safe!
    BigInteger operand1BigInt = new BigInteger(Long.toString(operand1));
    BigInteger result = operand1BigInt.pow((int) operand2);

    return truncateToBounds(result);
  }

  private int truncateToBounds(BigInteger value) {
    if (isAboveBound(value)) {
      return Integer.MAX_VALUE;
    }
    if (isBelowBound(value)) {
      return Integer.MIN_VALUE;
    }
    return value.intValue();
  }

  private boolean isAboveBound(BigInteger value) {
    return value.compareTo(new BigInteger(Integer.toString(Integer.MAX_VALUE))) > 0;
  }

  private boolean isBelowBound(BigInteger value) {
    return value.compareTo(new BigInteger(Integer.toString(Integer.MIN_VALUE))) < 0;
  }

  @Override
  public String getReadableName() {
    return "^";
  }
}
