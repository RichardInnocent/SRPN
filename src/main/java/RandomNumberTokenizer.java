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
