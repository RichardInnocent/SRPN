import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The result of a tokenization process. This is mutated through the various stages of compiling the
 * original command into a list of tokens.
 */
public class TokenizationResultBuilder {

  private final String command;
  private final List<Token> tokens = new ArrayList<>();

  private int currentIndex = 0;

  /**
   * Creates a new result builder.
   * @param command The command to be compiled into tokens.
   * @throws NullPointerException Thrown if {@code command == null};
   */
  public TokenizationResultBuilder(String command) throws NullPointerException {
    this.command = Objects.requireNonNull(command, "Command is null");
  }

  /**
   * Adds the token to the result.
   * @param token The token to add.
   * @throws NullPointerException Thrown if {@code token == null}.
   */
  public void addToken(Token token) throws NullPointerException {
    tokens.add(Objects.requireNonNull(token, "Token is null"));
  }

  /**
   * Sets the current index, i.e. the index of the next character that has not yet been compiled.
   * @param index The index.
   * @throws IllegalArgumentException Thrown if {@code index < getCurrentIndex()} - this could lead
   * to a cyclic tokenization process.
   */
  public void setCurrentIndex(int index) throws IllegalArgumentException {
    if (index < this.currentIndex) {
      throw new IllegalArgumentException(
          String.format(
              "Cannot revert the current index to an earlier position as this could cause a cyclic "
                  + "tokenization process. Current index: %d. Specified index: %d.",
              this.currentIndex, index
          )
      );
    }
    this.currentIndex = index;
  }

  /**
   * Gets the current index, i.e. the index of the next character that has not yet been compiled.
   * @return The index. Note that this might be out of bounds of the original command, and should
   * be used in conjunction with {@link #isComplete()}.
   */
  public int getCurrentIndex() {
    return currentIndex;
  }

  /**
   * Increments the current index, i.e. the index of the next character that has not yet been
   * compiled, by 1.
   */
  public void incrementCurrentIndex() {
    currentIndex++;
  }

  /**
   * Increments the current index, i.e. the index of the next character that has not yet been
   * compiled, by the specified amount.
   * @param amount The amount to increment the index by.
   * @throws IllegalArgumentException Thrown if {@code amount < 0} as this could cause a cyclic
   * tokenization process.
   */
  public void incrementCurrentIndexBy(int amount) throws IllegalArgumentException {
    if (amount < 0) {
      throw new IllegalArgumentException(
          "Cannot revert the current index to an earlier position as this could cause a cyclic "
              + "tokenization process. Attempted to increase the index by " + amount
      );
    }
    currentIndex += amount;
  }

  /**
   * Determines if the tokenization process for the command is complete.
   * @return {@code true} if all characters in the command have been parsed.
   */
  public boolean isComplete() {
    return currentIndex >= command.length();
  }

  /**
   * Gets all of the tokens that have been added so far in the tokenization process.
   * @return All of the tokens that have been added so far in the tokenization process. Note that
   * a copy of the original collection is returned, so modifying the returned collection will not
   * affect this instance.
   */
  public List<Token> getTokens() {
    return new ArrayList<>(tokens);
  }

  /**
   * Gets the original command that was attempted to be tokenized.
   * @return The original command.
   */
  public String getOriginalCommand() {
    return command;
  }

  /**
   * Gets the remaining command that is yet to be parsed.
   * @return The remaining command that is yet to be parsed. If the tokenization process is
   * complete, this will return an empty string.
   */
  public String getUnprocessedCommand() {
    if (currentIndex == 0) {
      // Not necessary but saves initialising a redundant string object
      return command;
    }
    if (isComplete()) {
      /* If there's no remaining command, return an empty string. This allows us to use the String
       * pool and avoid StringIndexOutOfBoundsExceptions. */
      return "";
    }
    return command.substring(currentIndex);
  }

  /**
   * Gets the next character that is yet to be parsed.
   * @return The next character that is yet to be parsed. If the tokenization process is complete,
   * this will return the null character ({@code \0}).
   */
  public char getNextUnprocessedCharacter() {
    // Return the next unprocessed character. If everything is processed, this should never be
    // called, but returning the null
    return isComplete() ? '\0' : command.charAt(currentIndex);
  }

}
