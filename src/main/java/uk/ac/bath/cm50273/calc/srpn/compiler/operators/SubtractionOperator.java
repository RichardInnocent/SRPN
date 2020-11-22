package uk.ac.bath.cm50273.calc.srpn.compiler.operators;

public class SubtractionOperator extends Operator {

  @Override
  public int apply(int operand1, int operand2) {
    return truncateToBounds(((long) operand1) - ((long) operand2));
  }

  @Override
  public String getReadableName() {
    return "-";
  }
}
