/**
 * Tokenizes numbers.
 */
public class IntegerTokenizer extends MultiCharacterTokenTokenizer<IntegerToken> {

  /**
   * Attempts to convert the {@code tokenString} into an {@link IntegerToken}. The
   * {@link IntegerToken#forValue(String)} factory method should handle strings of all different
   * lengths.
   * @param tokenString The text to convert.
   * @return A token to represent the operand.
   * @throws NumberFormatException Thrown if the {@code tokenString} doesn't represent an integer.
   * @throws DummySegmentationFaultException Thrown if {@code tokenString.length() > 120}. This is
   * a quirk with the legacy SRPN that we aim to replicate.
   */
  @Override
  protected IntegerToken buildToken(String tokenString)
      throws NumberFormatException, DummySegmentationFaultException {
    return IntegerToken.forValue(tokenString);
  }

  @Override
  public boolean canTokenize(char character) {
    // Can only tokenize characters if they're a number (digit).
    return Character.isDigit(character);
  }
}
