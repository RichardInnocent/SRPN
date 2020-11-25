import static org.junit.Assert.*;

import org.junit.Test;

public class EqualsTokenizerTest {

  private static final Tokenizer<EqualsToken> TOKENIZER = new EqualsTokenizer();

  @Test
  public void testCanTokenize() {
    TokenizerTestUtils.assertTokenizerCanOnlyTokenize(TOKENIZER, '=');
  }

  @Test
  public void testBuildToken() {
    String command = "A command";
    TokenizationResult<EqualsToken> result = TOKENIZER.tokenize(command);
    assertNotNull(result.getToken());
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

  @Test
  public void testHashCode() {
    assertEquals("equals".hashCode(), new EqualsToken().hashCode());
  }

  @Test
  public void testEquals() {
    assertEquals(new EqualsToken(), new EqualsToken());
    assertFalse(new EqualsToken().equals(new WhitespaceToken()));
  }

}