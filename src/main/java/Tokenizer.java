public interface Tokenizer {

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
   * @throws TokenizationException Thrown if there is a problem tokenizing the result.
   */
  ExecutionState attemptTokenization(TokenizationResultBuilder resultBuilder)
      throws TokenizationException;

}
