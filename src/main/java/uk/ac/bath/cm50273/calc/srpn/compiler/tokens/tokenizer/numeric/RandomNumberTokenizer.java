package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric;

import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric.RandomNumberToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.SingleCharacterTokenTokenizer;

/**
 * Tokenizes the random number operator.
 */
public class RandomNumberTokenizer extends SingleCharacterTokenTokenizer<RandomNumberToken> {

  @Override
  protected RandomNumberToken buildToken(char character) {
    return new RandomNumberToken();
  }

  @Override
  public boolean canTokenize(char character) {
    return character == 'r';
  }
}
