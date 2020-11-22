package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.misc;

import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc.EqualsToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.SingleCharacterTokenTokenizer;

/**
 * Tokenizes the equals operator.
 */
public class EqualsTokenizer extends SingleCharacterTokenTokenizer<EqualsToken> {

  @Override
  protected EqualsToken buildToken(char character) {
    return new EqualsToken();
  }

  @Override
  public boolean canTokenize(char character) {
    return character == '=';
  }
}
