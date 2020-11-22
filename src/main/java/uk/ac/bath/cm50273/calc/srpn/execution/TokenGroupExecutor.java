package uk.ac.bath.cm50273.calc.srpn.execution;

import java.util.Iterator;
import java.util.List;
import uk.ac.bath.cm50273.calc.srpn.CalculatorException;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.Token;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.TokenGroup;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc.EqualsToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric.IntegerToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric.RandomNumberToken;

public class TokenGroupExecutor {

  private final SizeRestrictedStack<Integer> operandStack = new SizeRestrictedStack<>(23);

  public void execute(List<TokenGroup> tokenGroups) {
    tokenGroups.forEach(this::execute);
  }

  public void execute(TokenGroup tokenGroup) {
    List<Token> tokens = tokenGroup.getTokens();
    Iterator<Token> tokenIterator = tokens.iterator();
    while (tokenIterator.hasNext()) {
      Token token = tokenIterator.next();
      if (token instanceof IntegerToken ||
          token instanceof EqualsToken ||
          token instanceof RandomNumberToken) {
        applyTokenAndHandleExceptions(token);
        tokenIterator.remove();
      }
    }

    tokens.forEach(this::applyTokenAndHandleExceptions);
  }

  private void applyTokenAndHandleExceptions(Token token) {
    try {
      token.apply(operandStack);
    } catch (CalculatorException e) {
      System.out.println(e.getMessage());
    }
  }

}
