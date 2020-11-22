package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer;

import java.util.Objects;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.Token;

/**
 * Stores the result of a successful tokenization attempt. It consists of the token that was
 * created, as well as the remainder of the command that is yet to be tokenized.
 * @param <T> The type of token.
 */
public class TokenizationResult<T extends Token> {

  private final T token;
  private final String remainingCommand;

  // Private constructor to force use of static factory method which makes it more clear what the
  // T and String parameters are
  private TokenizationResult(T token, String remainingCommand) throws NullPointerException {
    this.token = Objects.requireNonNull(token, "Token is null");
    this.remainingCommand = Objects.requireNonNull(remainingCommand, "Remaining command is null");
  }

  /**
   * Gets the token that was created by the tokenizer.
   * @return The token that was created by the tokenizer.
   */
  public T getToken() {
    return token;
  }

  /**
   * Gets the remaining command that was unable to be interpreted by the tokenizer.
   * @return The remaining command that was unable to be interpreted by the tokenizer. Note that
   * this will never be {@code null}, but could be empty.
   */
  public String getRemainingCommand() {
    return remainingCommand;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TokenizationResult)) {
      return false;
    }
    TokenizationResult<?> that = (TokenizationResult<?>) o;
    return Objects.equals(token, that.token) &&
        Objects.equals(remainingCommand, that.remainingCommand);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, remainingCommand);
  }

  /**
   * Creates a tokenizer result, i.e. the result of a tokenizer attempting to tokenize a command.
   * @param token The token returned by the tokenizer.
   * @param remainingCommand The remaining command, i.e. the remaining command that was unable to
   * tokenized by this tokenizer.
   * @param <T> The type of token held.
   * @return A new result.
   * @throws NullPointerException Thrown if {@code token == null} or
   * {@code remainingCommand == null}. If there is no command remaining, {@code remainingCommand}
   * should be set to {@code ""}.
   */
  public static <T extends Token> TokenizationResult<T> forTokenAndRemainingCommand(
      T token, String remainingCommand) throws NullPointerException {
    return new TokenizationResult<>(token, remainingCommand);
  }

}
