package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric;

import uk.ac.bath.cm50273.calc.srpn.CalculatorException;

public class DivideByZeroException extends CalculatorException {

  public DivideByZeroException() {
    super("Divide by 0.");
  }

}
