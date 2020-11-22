package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator;

import uk.ac.bath.cm50273.calc.srpn.compiler.operators.MultiplicationOperator;
import uk.ac.bath.cm50273.calc.srpn.compiler.operators.Operator;

public class MultiplicationToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    return new MultiplicationOperator();
  }
}
