package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.misc;

import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc.DisplayStackToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.SingleCharacterTokenTokenizer;

/**
 * Tokenizes the result history command.
 */
public class ResultHistoryTokenizer extends SingleCharacterTokenTokenizer<DisplayStackToken> {

  @Override
  protected DisplayStackToken buildToken(char character) {
    return new DisplayStackToken();
  }

  @Override
  public boolean canTokenize(char character) {
    return character == 'd';
  }
}
