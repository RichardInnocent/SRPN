import java.util.Optional;

/**
 * Tokenizes the display stack instruction.
 */
public class DisplayStackTokenizer extends SingleCharacterTokenTokenizer {

  @Override
  protected Optional<Token> buildToken(char character) {
    return character == 'd' ? Optional.of(new DisplayStackToken()) : Optional.empty();
  }

}
