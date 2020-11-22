package uk.ac.bath.cm50273.calc.srpn.compiler.tokens;

import uk.ac.bath.cm50273.calc.srpn.CalculatorException;
import uk.ac.bath.cm50273.calc.srpn.execution.SizeRestrictedStack;

public interface Token {

  void apply(SizeRestrictedStack<Integer> operandStack) throws CalculatorException;
  String getReadableValue();

}
