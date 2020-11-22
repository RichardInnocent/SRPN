package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator;

import uk.ac.bath.cm50273.calc.srpn.compiler.operators.AdditionOperator;
import uk.ac.bath.cm50273.calc.srpn.compiler.operators.Operator;

public class AdditionToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    return new AdditionOperator();
  }

}
