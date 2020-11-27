import java.util.Optional;

/**
 * Tokenizes the random number operator.
 */
public class RandomNumberTokenizer extends SingleCharacterTokenTokenizer<RandomNumberToken> {

  @Override
  protected Optional<RandomNumberToken> buildToken(char character) {
    return character == 'r' ? Optional.of(new RandomNumberToken()) : Optional.empty();
  }

}
