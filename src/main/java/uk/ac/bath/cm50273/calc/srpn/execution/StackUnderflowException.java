package uk.ac.bath.cm50273.calc.srpn.execution;

import uk.ac.bath.cm50273.calc.srpn.CalculatorException;

public class StackUnderflowException extends CalculatorException {

  public StackUnderflowException() {
    super("Stack underflow.");
  }

}
