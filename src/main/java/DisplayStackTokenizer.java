import java.util.Optional;

/**
 * Tokenizes the display stack instruction, denoted by {@code d}.
 */
public class DisplayStackTokenizer extends SingleCharacterTokenTokenizer<DisplayStackToken> {

  @Override
  protected Optional<DisplayStackToken> buildToken(char character) {
    return character == 'd' ? Optional.of(new DisplayStackToken()) : Optional.empty();
  }

}
