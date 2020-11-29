import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * Responsible for executing the tokens in a group or groups. Groups are executed sequentially.
 * When executing a group, all tokens are applied immediately, except for {@link OperandToken}s.
 * These tokens are added to a separate operator stack, where they remain until either:
 * <ul>
 *   <li>the next detected operator has a lower precedence</li>
 *   <li>the end of the group is reached</li>
 * </ul>
 * At this point, the operator stack is flushed, applying each token to the operand stack in turn.
 */
public class TokenGroupExecutor {

  private final SizeRestrictedStack<Double> operandStack;

  /**
   * Creates a new executor, responsible for executing grouped tokens. This uses the default operand
   * stack.
   */
  public TokenGroupExecutor() {
    this(new SizeRestrictedStack<>(23));
  }

  /**
   * Creates a new executor, responsible for executing grouped tokens. This is provided for testing
   * as it allows the operandStack to be injected.
   * @param operandStack The operand stack.
   * @throws NullPointerException Thrown if {@code operandStack == null}.
   */
  TokenGroupExecutor(SizeRestrictedStack<Double> operandStack) throws NullPointerException {
    this.operandStack = Objects.requireNonNull(operandStack, "Operand stack is null");
  }

  /**
   * Executes all of the tokens in each group, making sure to complete the processing of one group
   * before moving onto the next.
   * @param tokenGroups The groups of tokens.
   */
  public void execute(List<TokenGroup> tokenGroups) {
    tokenGroups.forEach(this::execute);
  }

  /**
   * Executes all of the tokens in the group.
   * @param tokenGroup The group of tokens to execute.
   */
  public void execute(TokenGroup tokenGroup) {
    if (tokenGroup.isEmpty()) {
      // Not required, but it saves initialising redundant objects
      return;
    }

    List<Token> tokens = tokenGroup.getTokens();
    Stack<OperatorToken> operatorTokens = new Stack<>();

    // Iterate over every token
    for (Token token : tokens) {

      // Operators should be delayed in execution, instead added to a separate operator stack
      if (token instanceof OperatorToken) {
        OperatorToken operatorToken = (OperatorToken) token;

        if (operatorTokens.size() > 0 &&
            operatorToken.hasLowerPrecedenceThan(operatorTokens.peek())) {
          /* If this operator should be processed after the previous operator, apply all of the
           * operators in the stack before adding this one. */
          flushOperators(operatorTokens);
        }
        operatorTokens.push(operatorToken);

      } else {
        // All other tokens should be applied immediately
        applyTokenAndHandleExceptions(token);
      }
    }

    flushOperators(operatorTokens);
  }

  private void applyTokenAndHandleExceptions(Token token) {
    try {
      token.apply(operandStack);
    } catch (CalculatorException e) {
      /* If there's an exception, print this to stdout. As we can determine by directing error
       * messages to /dev/null in the legacy SRPN, these errors are not printed to stderr. */
      System.out.println(e.getMessage());
    }
  }

  private void flushOperators(Stack<OperatorToken> operatorTokens) {
    // Apply each operator to the operator stack in turn
    while (!operatorTokens.isEmpty()) {
      applyTokenAndHandleExceptions(operatorTokens.pop());
    }
  }

}
