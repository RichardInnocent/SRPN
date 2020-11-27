public abstract class AbstractTokenizer implements Tokenizer {

  @Override
  public ExecutionState attemptTokenization(TokenizationResultBuilder resultBuilder)
      throws TokenizationException {
    try {
      return attemptTokenizationAndThrowExceptions(resultBuilder);
    } catch (Exception e) {
      throw new TokenizationException(
          "Failed to generate next token from remaining command "
              + resultBuilder.getUnprocessedCommand(),
          e
      );
    }
  }

  protected abstract ExecutionState attemptTokenizationAndThrowExceptions(
      TokenizationResultBuilder resultBuilder) throws Exception;

}
