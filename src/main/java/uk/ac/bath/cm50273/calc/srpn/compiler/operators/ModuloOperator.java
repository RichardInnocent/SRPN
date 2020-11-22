package uk.ac.bath.cm50273.calc.srpn.compiler.operators;

import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric.DummyFloatingPointException;

public class ModuloOperator extends Operator {

  @Override
  public int apply(int operand1, int operand2) throws DummyFloatingPointException {
    if (operand2 == 0) {
      throw new DummyFloatingPointException();
    }
    return operand1 % operand2;
  }

  @Override
  public String getReadableName() {
    return "%";
  }
}
