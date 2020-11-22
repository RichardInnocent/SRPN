package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator;

import uk.ac.bath.cm50273.calc.srpn.compiler.operators.ModuloOperator;
import uk.ac.bath.cm50273.calc.srpn.compiler.operators.Operator;

public class ModuloToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    return new ModuloOperator();
  }

}