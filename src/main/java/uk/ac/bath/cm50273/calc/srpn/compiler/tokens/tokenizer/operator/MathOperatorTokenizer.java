package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.operator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.AdditionToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.DivisionToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.ModuloToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.MultiplicationToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.OperatorToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.RaiseToPowerToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.SubtractionToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.SingleCharacterTokenTokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.TokenizationException;

/**
 * Tokenizes single-digit mathematical operators, such as {@code +} and {@code -}.
 */
public class MathOperatorTokenizer extends SingleCharacterTokenTokenizer<OperatorToken> {

  private final Map<Character, Supplier<? extends OperatorToken>> tokenSuppliers;

  public MathOperatorTokenizer() {
    /* Populate the token suppliers. When we encounter one of the characters in the key, we can
     * just call the supplier get method to create our token. */
    tokenSuppliers = new HashMap<>(6);
    tokenSuppliers.put('+', AdditionToken::new);
    tokenSuppliers.put('-', SubtractionToken::new);
    tokenSuppliers.put('/', DivisionToken::new);
    tokenSuppliers.put('*', MultiplicationToken::new);
    tokenSuppliers.put('%', ModuloToken::new);
    tokenSuppliers.put('^', RaiseToPowerToken::new);
  }

  @Override
  protected OperatorToken buildToken(char character) {
    Supplier<? extends OperatorToken> supplier = tokenSuppliers.get(character);
    if (supplier == null) {
      throw new TokenizationException("No supplier found for character " + character);
    }
    // Build the token from the supplier
    return supplier.get();
  }

  @Override
  public boolean canTokenize(char character) {
    return tokenSuppliers.containsKey(character);
  }
}
