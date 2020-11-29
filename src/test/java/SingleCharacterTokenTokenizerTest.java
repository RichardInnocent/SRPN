import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Optional;
import org.junit.Test;

public class SingleCharacterTokenTokenizerTest {

  private static final Token TOKEN_RESULT = mock(Token.class);
  private static final SingleCharacterTokenTokenizer<Token> TOKENIZER = new SingleCharacterTokenTokenizer<Token>() {
    @Override
    protected Optional<Token> buildToken(char character) {
      return character == 'a' ? Optional.of(TOKEN_RESULT) : Optional.empty();
    }
  };

  @Test
  public void testTokenizingFirstCharacterSuccessfully() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("abacus");
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    List<Token> tokens = resultBuilder.getTokens();
    assertEquals(1, tokens.size());
    assertSame(TOKEN_RESULT, tokens.get(0));
    assertEquals(1, resultBuilder.getCurrentIndex());
  }

  @Test
  public void testTokenizingMiddleCharacterSuccessfully() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("abacus");
    resultBuilder.setCurrentIndex(2);
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    List<Token> tokens = resultBuilder.getTokens();
    assertEquals(1, tokens.size());
    assertSame(TOKEN_RESULT, tokens.get(0));
    assertEquals(3, resultBuilder.getCurrentIndex());
  }

  @Test
  public void testTokenizingLastCharacterSuccessfully() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("era");
    resultBuilder.setCurrentIndex(2);
    assertTrue(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    List<Token> tokens = resultBuilder.getTokens();
    assertEquals(1, tokens.size());
    assertSame(TOKEN_RESULT, tokens.get(0));
    assertTrue(resultBuilder.isComplete());
  }

  @Test
  public void testUnsuccessfulTokenization() {
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder("cave");
    assertFalse(TOKENIZER.attemptTokenization(resultBuilder).succeeded());
    assertTrue(resultBuilder.getTokens().isEmpty());
    assertEquals(0, resultBuilder.getCurrentIndex());
  }

}