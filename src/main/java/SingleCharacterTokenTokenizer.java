import java.util.Optional;

/**
 * A tokenizer for tokens that aren't expected to be a single character in length, i.e. adding
 * additional characters would be invalid or have no meaning. For example, an addition token
 * ({@code +}) is only relevant to itself - another symbol appended after this must be a separate
 * token. Neither {@code ++} nor {@code +=} etc are valid tokens in this calculator.
 */
public abstract class SingleCharacterTokenTokenizer<T extends Token> extends AbstractTokenizer {

  @Override
  protected ExecutionState attemptTokenizationAndThrowExceptions(
      TokenizationResultBuilder resultBuilder) throws Exception {
    // Try to build the token from the character at the start of the unprocessed command
    Optional<T> token = buildToken(resultBuilder.getNextUnprocessedCharacter());

    if (token.isPresent()) {
      // The tokenization was successful - add the token and consume this character
      resultBuilder.addToken(token.get());
      resultBuilder.incrementCurrentIndex();
      return ExecutionState.SUCCEEDED;
    }

    // Tokenization was unsuccessful - don't advance the command
    return ExecutionState.FAILED;
  }

  /**
   * Converts the text into a token.
   * @param character The character to convert.
   * @return A token.
   * @throws Exception Thrown if there is a problem converting the character into a token.
   */
  protected abstract Optional<T> buildToken(char character) throws Exception;
}
