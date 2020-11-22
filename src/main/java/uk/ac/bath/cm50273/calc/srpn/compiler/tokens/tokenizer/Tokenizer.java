package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer;

import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.Token;

/**
 * Responsible for turning a single character or string of characters into a token.
 * @param <T> The type of token that will be returned from this tokenizer.
 */
public interface Tokenizer<T extends Token> {

  /**
   * Determines if the tokenizer can tokenize the given character.
   * @param character The character to tokenize.
   * @return {@code true} if the tokenizer can tokenize the given character.
   */
  boolean canTokenize(char character);

  /**
   * Transforms as much of the given command as it can. The tokenizer starts at the beginning of the
   * string and continues tokenizing the result until any of the following are met:
   * <ul>
   *   <li>The token is complete.</li>
   *   <li>The next character cannot be tokenized by this tokenizer.</li>
   *   <li>The command is fully processed.</li>
   * </ul>
   * @param command The command to tokenize.
   * @return The result, which includes the token that was created, and the remaining command that
   * was unable to be tokenized.
   */
  TokenizationResult<T> tokenize(String command);

}
