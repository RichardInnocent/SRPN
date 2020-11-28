import static org.junit.Assert.*;

import java.util.Optional;
import org.junit.Test;

public class MathOperatorTokenizerTest {

  private static final MathOperatorTokenizer TOKENIZER = new MathOperatorTokenizer();

  @Test
  public void testCanTokenizePlus() {
    Optional<OperatorToken> token = TOKENIZER.buildToken('+');
    assertTrue(token.isPresent());
    assertTrue(token.get() instanceof AdditionToken);
  }

  @Test
  public void testCanTokenizeMinus() {
    Optional<OperatorToken> token = TOKENIZER.buildToken('-');
    assertTrue(token.isPresent());
    assertTrue(token.get() instanceof SubtractionToken);
  }

  @Test
  public void testCanTokenizeForwardSlash() {
    Optional<OperatorToken> token = TOKENIZER.buildToken('/');
    assertTrue(token.isPresent());
    assertTrue(token.get() instanceof DivisionToken);
  }

  @Test
  public void testCanTokenizeAsterisk() {
    Optional<OperatorToken> token = TOKENIZER.buildToken('*');
    assertTrue(token.isPresent());
    assertTrue(token.get() instanceof MultiplicationToken);
  }

  @Test
  public void testCanTokenizePercent() {
    Optional<OperatorToken> token = TOKENIZER.buildToken('%');
    assertTrue(token.isPresent());
    assertTrue(token.get() instanceof ModuloToken);
  }

  @Test
  public void testCanTokenizeCaret() {
    Optional<OperatorToken> token = TOKENIZER.buildToken('^');
    assertTrue(token.isPresent());
    assertTrue(token.get() instanceof RaiseToPowerToken);
  }

  @Test
  public void testFailedTokenization() {
    assertFalse(TOKENIZER.buildToken('p').isPresent());
  }

}