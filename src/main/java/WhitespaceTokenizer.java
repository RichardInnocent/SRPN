/**
 * Tokenizes whitespace. Whitespace is detected whenever a character meets the criteria
 * {@link Character#isWhitespace(char)}.
 */
public class WhitespaceTokenizer extends AbstractTokenizer {

  /**
   * Attempts to tokenize the next section of the command as whitespace. The whitespace is grouped
   * together, such that if there are multiple whitespace characters, these are tokenized as a
   * single whitespace character.
   * @param resultBuilder The tokenization result. This is a mutable object that is build upon by
   * the varies {@link Tokenizer}s as the command is compiled.
   * @return The state of the tokenization attempt.
   */
  @Override
  protected ExecutionState attemptTokenizationAndThrowExceptions(
      TokenizationResultBuilder resultBuilder) {
    String unprocessedCommand = resultBuilder.getUnprocessedCommand();

    // A running count on how many whitespace characters were detected
    int processedCharacters = 0;

    /* Iterate through the unprocessed command, stopping when we either reach a non-whitespace
     * character, or we reach the end of the command. */
    while (processedCharacters < unprocessedCommand.length() &&
        Character.isWhitespace(unprocessedCommand.charAt(processedCharacters))) {
      // This is a whitespace character
      processedCharacters++;
    }

    if (processedCharacters > 0) {
      /* We processed one or more characters so consume those characters and add a whitespace token
       * to the result. */
      resultBuilder.addToken(new WhitespaceToken());
      resultBuilder.incrementCurrentIndexBy(processedCharacters);
      return ExecutionState.SUCCEEDED;
    }

    /* No whitespace characters were found so this tokenizer failed to tokenize this section of the
     * command. */
    return ExecutionState.FAILED;
  }
  
}
