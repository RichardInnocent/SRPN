package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator;

import uk.ac.bath.cm50273.calc.srpn.compiler.operators.DivisionOperator;
import uk.ac.bath.cm50273.calc.srpn.compiler.operators.Operator;

public class DivisionToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    return new DivisionOperator();
  }

}
