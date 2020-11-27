public interface Tokenizer {

  ExecutionState attemptTokenization(TokenizationResultBuilder resultBuilder)
      throws TokenizationException;

}
