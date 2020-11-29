/**
 * Abstract implementation of a {@link Tokenizer}. This abstract implementation wraps possible
 * exceptions thrown from the underlying tokenization process in a {@link TokenizationException}.
 * This should improve the ease at which bugs can be diagnosed and identified.
 */
public abstract class AbstractTokenizer implements Tokenizer {

  @Override
  public ExecutionState attemptTokenization(TokenizationResultBuilder resultBuilder)
      throws TokenizationException {
    // Try to return the result, or wrap the exceptions if appropriate
    try {
      return attemptTokenizationAndThrowExceptions(resultBuilder);
    } catch (FatalSrpnException e) {
      /* FatalSrpnExceptions are meant to replicate the behaviour of the original SRPN so throw
       * these as is. */
      throw e;
    } catch (Exception e) {
      /* Wrap any exceptions in another exception type that more accurately conveys where the
       * problem area is. */
      throw new TokenizationException(
          "Failed to generate next token from remaining command "
              + resultBuilder.getUnprocessedCommand(),
          e
      );
    }
  }

  /**
   * Attempts the tokenization of the command from the current position. The underlying
   * implementation is responsible for updating the index if tokenization is successful.
   * @param resultBuilder The tokenization result. This is a mutable object that is build upon by
   * the varies {@link Tokenizer}s as the command is compiled.
   * @return Whether this tokenizer was successful in tokenizing the result. Note that an
   * {@link ExecutionState} is returned rather than a {@code boolean}. This is to aid readability -
   * consider the difference in readability between the following:
   * <pre><code>if (tokenizer.attemptTokenization()) { ... }</code></pre>
   * <pre><code>if (tokenizer.attemptTokenization().succeeded()) { ... }</code></pre>
   * @throws Exception Thrown if there is a problem tokenizing the result.
   */
  protected abstract ExecutionState attemptTokenizationAndThrowExceptions(
      TokenizationResultBuilder resultBuilder) throws Exception;

}
