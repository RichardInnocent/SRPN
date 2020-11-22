package uk.ac.bath.cm50273.calc.srpn.compiler.tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric.IntegerToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.SubtractionToken;

public class TokenGroup {

  private final List<Token> tokens = new ArrayList<>();

  public void addAndTidy(Token token) {
    if (tokens.size() == 1 && isMinusSign(tokens.get(0)) && isNumber(token)) {
      /* This relates to the case where the group starts with a minus sign and then has a number.
       * In this case, the minus sign should be applied to the value and not be interpreted as an
       * operator. Note that there is a difference in the calculator between -2147483648 and
       * 0-2147483648, the latter being evaluated at -2147483647. */
      tokens.clear();
      tokens.add(((IntegerToken) token).flipSign());
    } else {
      tokens.add(token);
    }
  }

  private boolean isMinusSign(Token token) {
    return token instanceof SubtractionToken;
  }

  private boolean isNumber(Token token) {
    return token instanceof IntegerToken;
  }

  public List<Token> getTokens() {
    return new ArrayList<>(tokens);
  }

  public Token getToken(int index) {
    return tokens.get(index);
  }

  public Stream<Token> streamTokens() {
    return tokens.stream();
  }

  public boolean isEmpty() {
    return tokens.isEmpty();
  }

  public int size() {
    return tokens.size();
  }

}
