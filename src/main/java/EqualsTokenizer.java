/**
 * Tokenizes the equals operator.
 */
public class EqualsTokenizer extends SingleCharacterTokenTokenizer<EqualsToken> {

  @Override
  protected EqualsToken buildToken(char character) {
    /* We can already assume that the character will be an {@code =} sign, so we can just build an
     * EqualsToken. The token has no internal state. */
    return new EqualsToken();
  }

  @Override
  public boolean canTokenize(char character) {
    // Can only tokenize if the character is the equals sign
    return character == '=';
  }
}
