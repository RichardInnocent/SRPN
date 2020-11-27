import java.util.Optional;

/**
 * Tokenizes the equals operator.
 */
public class EqualsTokenizer extends SingleCharacterTokenTokenizer<EqualsToken> {

  @Override
  protected Optional<EqualsToken> buildToken(char character) {
    return character == '=' ? Optional.of(new EqualsToken()) : Optional.empty();
  }

}
