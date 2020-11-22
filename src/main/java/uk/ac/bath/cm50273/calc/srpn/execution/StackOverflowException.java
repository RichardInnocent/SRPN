package uk.ac.bath.cm50273.calc.srpn.execution;

import uk.ac.bath.cm50273.calc.srpn.CalculatorException;

public class StackOverflowException extends CalculatorException {

  public StackOverflowException() {
    super("Stack overflow.");
  }

}
