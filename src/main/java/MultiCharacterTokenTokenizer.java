/**
 * A tokenizer for tokens that could potentially be longer than a single character in length. For
 * example, a number could be a single digit ({@code 3}) or multiple digits ({@code 34}). This
 * tokenizer groups all related characters together to make a single token. As such, {@code 34}
 * would become a single token rather than a separate token per digit.
 * @param <T> The type of token that will be returned from the tokenizer.
 */
public abstract class MultiCharacterTokenTokenizer<T extends Token> implements Tokenizer<T> {

  @Override
  public TokenizationResult<T> tokenize(String command) throws TokenizationException {
    // The token string will always consist of at least the first character
    StringBuilder tokenString = new StringBuilder(command.substring(0, 1));

    /* The token will consist of the first character plus each subsequent character up until the
     * next character that is not tokenizable by this tokenizer. e.g. "1234+12" should tokenize
     * the first 4 characters, and then terminate when it reaches the + symbol. */
    int i = 1;
    while (i < command.length() && canTokenize(command.charAt(i))) {
      tokenString.append(command.charAt(i));
      i++;
    }

    T token = buildTokenAndWrapException(tokenString.toString());

    /* The remaining command is everything else that wasn't tokenizable by this tokenizer. This
     * will still need to be processed, but we can remove everything we tokenized successfully. */
    String remainingCommand = command.substring(i);
    return TokenizationResult.forTokenAndRemainingCommand(token, remainingCommand);
  }

  /**
   * This attempts to tokenize the string. If an exception if thrown from the underlying
   * {@link #buildToken(String)} method, this exception is caught and wrapped in a
   * {@link TokenizationException} so that the caller can be confident in its exception handling.
   * @param tokenString The textual representation of the token to be transformed.
   * @return A token.
   * @throws TokenizationException Thrown if the underlying {@link #buildToken(String)} method
   * throws any exceptions.
   * @throws DummySegmentationFaultException Thrown if the tokenization process would have caused
   * the emulated calculated to segfault. This is therefore an intentional exception thrown by the
   * tokenization process.
   */
  private T buildTokenAndWrapException(String tokenString)
      throws TokenizationException, DummySegmentationFaultException {
    try {
      return buildToken(tokenString);
    } catch (DummySegmentationFaultException e) {
      throw e; // Don't wrap this exception type - we want this to appear in its base form
    } catch (Exception e) {
      throw new TokenizationException("Failed to tokenize token " + tokenString, e);
    }
  }

  /**
   * Converts the text into a token.
   * @param tokenString The text to convert.
   * @return A token.
   * @throws Exception Thrown if there is a problem converting the text into a token.
   */
  protected abstract T buildToken(String tokenString) throws Exception;
}
