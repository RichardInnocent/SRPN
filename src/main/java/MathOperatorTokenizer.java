import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Tokenizes single-digit mathematical operators, such as {@code +} and {@code -}.
 */
public class MathOperatorTokenizer extends SingleCharacterTokenTokenizer<OperatorToken> {

  // There's a distinct mapping for each operator character to a particular operator type
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
  protected Optional<OperatorToken> buildToken(char character) {
    Supplier<? extends OperatorToken> supplier = tokenSuppliers.get(character);
    return supplier == null ? Optional.empty() : Optional.of(supplier.get());
  }

}
