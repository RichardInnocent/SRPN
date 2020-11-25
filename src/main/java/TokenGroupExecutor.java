import java.util.ArrayList;
import java.util.List;

public class TokenGroupExecutor {

  private final SizeRestrictedStack<Double> operandStack = new SizeRestrictedStack<>(23);

  public void execute(List<TokenGroup> tokenGroups) {
    tokenGroups.forEach(this::execute);
  }

  public void execute(TokenGroup tokenGroup) throws IllegalArgumentException {
    List<Token> tokens = tokenGroup.getTokens();
    List<OperatorToken> operatorTokens = new ArrayList<>();
    for (Token token : tokens) {
      if (token instanceof OperatorToken) {
        operatorTokens.add((OperatorToken) token);
      } else if (token instanceof ImmediatelyAppliedToken) {
        applyTokenAndHandleExceptions((ImmediatelyAppliedToken) token);
      } else {
        throw new IllegalArgumentException("Illegal token argument of type " + token.getClass());
      }
    }

    applyOperators(operatorTokens);
  }

  private void applyTokenAndHandleExceptions(ImmediatelyAppliedToken token) {
    try {
      token.apply(operandStack);
    } catch (CalculatorException e) {
      /* If there's an exception, print this to stdout. As we can determine by directing error
       * messages to /dev/null in the legacy SRPN, these errors are not printed to stderr. */
      System.out.println(e.getMessage());
    }
  }

  private void applyOperators(List<OperatorToken> operatorTokens) {
    applyAndRemoveTokensOfType(RaiseToPowerToken.class, operatorTokens);
    applyAndRemoveTokensOfType(ModuloToken.class, operatorTokens);
    applyAndRemoveTokensOfType(DivisionToken.class, operatorTokens);
    applyAndRemoveTokensOfType(MultiplicationToken.class, operatorTokens);
    applyAndRemoveTokensOfType(AdditionToken.class, operatorTokens);
    applyAndRemoveTokensOfType(SubtractionToken.class, operatorTokens);
  }

  private void applyAndRemoveTokensOfType(
      Class<? extends OperatorToken> tokenType, List<OperatorToken> operatorTokens) {
    int currentTokenIndex = 0;
    while (currentTokenIndex < operatorTokens.size()) {
      OperatorToken token = operatorTokens.get(currentTokenIndex);
      if (token.getClass().equals(tokenType)) {
        applyOperator(token.getOperator(), operatorTokens.size() - currentTokenIndex - 1);
        operatorTokens.remove(currentTokenIndex);
      } else {
        currentTokenIndex++;
      }
    }
  }

  private void applyOperator(Operator operator, int indexFromEnd) {
    try {
      applyOperatorAndThrowExceptions(operator, indexFromEnd);
    } catch (CalculatorException e) {
      System.out.println(e.getMessage());
    }
  }

  private void applyOperatorAndThrowExceptions(Operator operator, int indexFromEnd)
      throws CalculatorException {
    int operand1Index = operandStack.size() - indexFromEnd - 2;
    int operand2Index = operand1Index + 1;

    if (operand1Index < 0) {
      throw new StackUnderflowException();
    }

    double operand1 = operandStack.get(operand1Index);
    double operand2 = operandStack.get(operand2Index);

    double result = operator.apply(operand1, operand2);
    operandStack.setElementAt(result, operand1Index);
    operandStack.removeElementAt(operand2Index);
  }

}
