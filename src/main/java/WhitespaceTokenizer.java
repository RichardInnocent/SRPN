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
