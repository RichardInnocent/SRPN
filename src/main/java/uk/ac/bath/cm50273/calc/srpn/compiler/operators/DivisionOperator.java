package uk.ac.bath.cm50273.calc.srpn.compiler.operators;

import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric.DivideByZeroException;

public class DivisionOperator extends Operator {

  @Override
  public int apply(int operand1, int operand2) {
    if (operand2 == 0) {
      throw new DivideByZeroException();
    }
    return operand1 / operand2;
  }

  @Override
  public String getReadableName() {
    return "/";
  }
}
