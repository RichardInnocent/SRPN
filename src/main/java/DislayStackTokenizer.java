/**
 * Tokenizes the display stack instruction.
 */
public class DislayStackTokenizer extends SingleCharacterTokenTokenizer<DisplayStackToken> {

  @Override
  protected DisplayStackToken buildToken(char character) {
    /* If used appropriately, the character should be 'd'. We return a new DisplayStackToken, which
     * has no internal state. */
    return new DisplayStackToken();
  }

  @Override
  public boolean canTokenize(char character) {
    return character == 'd';
  }
}
