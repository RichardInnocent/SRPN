package uk.ac.bath.cm50273.calc.srpn.compiler.tokens;

public abstract class AbstractToken implements Token {

  @Override
  public String toString() {
    return getReadableValue();
  }

}
