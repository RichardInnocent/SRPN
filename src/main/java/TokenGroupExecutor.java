import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class TokenGroupExecutor {

  private final SizeRestrictedStack<Double> operandStack = new SizeRestrictedStack<>(23);

  public void execute(List<TokenGroup> tokenGroups) {
    tokenGroups.forEach(this::execute);
  }

  public void execute(TokenGroup tokenGroup) throws IllegalArgumentException {
    List<Token> tokens = tokenGroup.getTokens();
    Stack<OperatorToken> operatorTokens = new Stack<>();

    for (int i = 0; i < tokens.size(); i++) {
      Token token = tokens.get(i);
      if (token instanceof OperatorToken) {
        OperatorToken operatorToken = (OperatorToken) token;

        int numOperators = operatorTokens.size();
        if (numOperators > 0 && operatorToken.compareTo(operatorTokens.peek()) > 0) {
          flushOperators(operatorTokens);

          Optional<OperandToken> consecutivelyNextOperandToken = getOperandTokenAtIndex(i+1, tokens);
          if (consecutivelyNextOperandToken.isPresent()) {
            consecutivelyNextOperandToken.get().apply(operandStack);
            i++; // Skip the next value as it's already been added
          }
        }
        operatorTokens.push(operatorToken);

      } else {
        applyTokenAndHandleExceptions(token);
      }
    }

    flushOperators(operatorTokens);
  }

  private Optional<OperandToken> getOperandTokenAtIndex(int index, List<Token> tokens) {
    if (index >= tokens.size()) {
      return Optional.empty();
    }
    Token token = tokens.get(index);
    return token instanceof OperandToken ? Optional.of((OperandToken) token) : Optional.empty();
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
    if (operatorTokens.isEmpty()) {
      return;
    }

    while (!operatorTokens.isEmpty()) {
      try {
        operatorTokens.pop().apply(operandStack);
      } catch (CalculatorException e) {
        System.out.println(e.getMessage());
      }
    }
  }

}
