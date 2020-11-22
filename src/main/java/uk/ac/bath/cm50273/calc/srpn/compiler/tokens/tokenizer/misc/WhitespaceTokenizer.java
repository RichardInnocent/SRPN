package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.misc;

import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc.WhitespaceToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.MultiCharacterTokenTokenizer;

/**
 * Tokenizes whitespace.
 */
public class WhitespaceTokenizer extends MultiCharacterTokenTokenizer<WhitespaceToken> {

  @Override
  protected WhitespaceToken buildToken(String tokenString) throws NumberFormatException {
    return new WhitespaceToken();
  }

  @Override
  public boolean canTokenize(char character) {
    return Character.isWhitespace(character);
  }
}
