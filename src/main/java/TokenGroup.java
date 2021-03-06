import java.util.ArrayList;
import java.util.List;

/**
 * A token group represents a sequence of tokens that should be executed in one stage. Only one
 * group should be executed at a time.
 */
public class TokenGroup {

  private final List<Token> tokens = new ArrayList<>();

  /**
   * Adds the token to the list of tokens. If the token is an {@link OperandToken} and the previous
   * token is an {@link SubtractionToken}, these two tokens may be merged into one negative
   * {@code IntegerToken}.
   * @param token The token to add.
   */
  public void addAndTidy(Token token) {
    if (shouldHaveSignReversed(token)) {
      /* This relates to the case where the group starts with a minus sign and then has a number.
       * In this case, the minus sign should be applied to the value and not be interpreted as an
       * operator. Note that there is a difference in the calculator between -2147483648 and
       * 0-2147483648, the latter being evaluated at -2147483647. */
      tokens.remove(tokens.get(tokens.size()-1));
      tokens.add(((OperandToken) token).flipSign());
    } else {
      tokens.add(token);
    }
  }

  /**
   * <p>Determines if the current token is numeric and should have its sign reversed. This occurs if
   * all of the following conditions are met:</p>
   * <ul>
   *   <li>{@code token} is an {@link OperandToken}</li>
   *   <li>There is an odd number of consecutive {@link SubtractionToken}s prior to the operand.</li>
   *   <li>The consecutive {@link SubtractionToken}s occur at the start of the group, or after
   *   another operand.</li>
   * </ul>
   * <p>The last condition is particularly important as it ensures that {@code 3-5} is interpreted
   * as {@code [3] [-] [5]}, while {@code d-5} is interpreted as {@code [d] [-5]}. Also, strangely,
   * {@code +-3} is interpreted by the legacy SRPN as {@code [+] [-3]}, while {@code --3} is
   * interpreted as {@code [-] [-] [3]}.</p>
   *
   * <p>Note that there is a difference in the calculator between -2147483648 and 0-2147483648, the
   * latter being evaluated at -2147483647.</p>
   * @param token The token to interpret.
   * @return {@code true} if its sign should be reversed.
   */
  private boolean shouldHaveSignReversed(Token token) {
    if (!(isNumeric(token)) || tokens.isEmpty()) {
      // Token isn't numeric or there are no previous tokens so there can't be a previous minus sign
      return false;
    }

    int numberOfTrailingSubtractionTokens = getNumberOfTrailingSubtractionTokens();
    if (numberOfTrailingSubtractionTokens % 2 == 0) {
      return false;
    }
    return trailingSubtractionTokensAreAtStartOfGroupOrAfterOperator(
        numberOfTrailingSubtractionTokens);
  }

  private boolean trailingSubtractionTokensAreAtStartOfGroupOrAfterOperator(
      int numberOfTrailingSubtractionTokens) {
    int previousIndex = tokens.size() - numberOfTrailingSubtractionTokens - 1;
    return previousIndex < 0 || isOperator(tokens.get(previousIndex));
  }

  private int getNumberOfTrailingSubtractionTokens() {
    int count = 0;
    for (int i = tokens.size() - 1; i >= 0; i--) {
      if (isMinusSign(tokens.get(i))) {
        count++;
      } else {
        break;
      }
    }
    return count;
  }

  private boolean isMinusSign(Token token) {
    return token instanceof SubtractionToken;
  }

  private boolean isNumeric(Token token) {
    return token instanceof OperandToken;
  }

  private boolean isOperator(Token token) {
    return token instanceof OperatorToken;
  }

  /**
   * Gets all of the tokens in the group. Note that this returns a copy of the tokens in the group -
   * modifying the returned list will not affect this group.
   * @return The tokens in the group.
   */
  public List<Token> getTokens() {
    return new ArrayList<>(tokens);
  }

  /**
   * Gets the token at a particular index.
   * @param index The index of the token to retrieve.
   * @return The token at the specified index.
   * @throws IndexOutOfBoundsException Thrown if {@code index < 0 || index >= size()}.
   */
  public Token getToken(int index) throws IndexOutOfBoundsException {
    return tokens.get(index);
  }

  /**
   * Determines whether this group is empty, i.e. it contains no tokens.
   * @return {@code true} if this group contains no tokens.
   */
  public boolean isEmpty() {
    return tokens.isEmpty();
  }

  /**
   * Gets the number of tokens in the group.
   * @return The number of tokens in the group.
   */
  public int size() {
    return tokens.size();
  }

}
