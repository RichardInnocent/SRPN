package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric;

public class DummyFloatingPointException extends RuntimeException {

  public DummyFloatingPointException() {
    super("Floating point exception(core dumped)");
  }

}
