package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric;

import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric.IntegerToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.MultiCharacterTokenTokenizer;

/**
 * Tokenizes numbers.
 */
public class IntegerTokenizer extends MultiCharacterTokenTokenizer<IntegerToken> {

  @Override
  protected IntegerToken buildToken(String tokenString) throws NumberFormatException {
    return IntegerToken.forValue(tokenString);
  }

  @Override
  public boolean canTokenize(char character) {
    return Character.isDigit(character);
  }
}
