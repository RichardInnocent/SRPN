package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator;

import uk.ac.bath.cm50273.calc.srpn.compiler.operators.Operator;
import uk.ac.bath.cm50273.calc.srpn.compiler.operators.RaiseToPowerOperator;

public class RaiseToPowerToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    return new RaiseToPowerOperator();
  }
}
