/**
 * Tokenizes whitespace.
 */
public class WhitespaceTokenizer extends AbstractTokenizer {

  @Override
  protected ExecutionState attemptTokenizationAndThrowExceptions(
      TokenizationResultBuilder resultBuilder) {
    String unprocessedCommand = resultBuilder.getUnprocessedCommand();

    int processedCharacters = 0;
    while (processedCharacters < unprocessedCommand.length() &&
        Character.isWhitespace(unprocessedCommand.charAt(processedCharacters))) {
      processedCharacters++;
    }

    if (processedCharacters > 0) {
      resultBuilder.addToken(new WhitespaceToken());
      resultBuilder.incrementCurrentIndexBy(processedCharacters);
      return ExecutionState.SUCCEEDED;
    }
    return ExecutionState.FAILED;
  }
  
}
