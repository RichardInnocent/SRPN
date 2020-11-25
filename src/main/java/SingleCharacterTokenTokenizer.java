/**
 * A tokenizer for tokens that aren't expected to be a single character in length, i.e. adding
 * additional characters would be invalid or have no meaning. For example, an addition token
 * ({@code +}) is only relevant to itself - another symbol appended after this must be a separate
 * token. Neither {@code ++} nor {@code +=} etc are valid tokens in this calculator.
 * @param <T> The type of token that will be returned from the tokenizer.
 */
public abstract class SingleCharacterTokenTokenizer<T extends Token> implements Tokenizer<T> {

  @Override
  public TokenizationResult<T> tokenize(String command) throws TokenizationException {
    T token = tokenizeAndWrapException(command.charAt(0));
    String remainingCommand = command.substring(1);
    return TokenizationResult.forTokenAndRemainingCommand(token, remainingCommand);
  }

  /**
   * This attempts to tokenize the character. If an exception if thrown from the underlying
   * {@link #buildToken(char)} method, this exception is caught and wrapped in a
   * {@link TokenizationException} so that the caller can be confident in its exception handling.
   * @param character The raw character representation of the token to be transformed.
   * @return A token.
   * @throws TokenizationException Thrown if the underlying {@link #buildToken(char)} method
   * throws any exceptions.
   */
  private T tokenizeAndWrapException(char character) throws TokenizationException {
    try {
      return buildToken(character);
    } catch (Exception e) {
      throw new TokenizationException("Failed to tokenize token " + character, e);
    }
  }

  /**
   * Converts the text into a token.
   * @param character The character to convert.
   * @return A token.
   * @throws Exception Thrown if there is a problem converting the character into a token.
   */
  protected abstract T buildToken(char character) throws Exception;
}
