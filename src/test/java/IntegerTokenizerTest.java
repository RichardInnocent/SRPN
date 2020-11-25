import static org.junit.Assert.*;

import org.junit.Test;

public class IntegerTokenizerTest {

  private static final Tokenizer<IntegerToken> TOKENIZER = new IntegerTokenizer();

  @Test
  public void testCanTokenize() {
    TokenizerTestUtils.assertTokenizerCanOnlyTokenize(TOKENIZER, "1234567890");
  }

  @Test
  public void testBuildToken() {
    String command = "1234+56789";
    TokenizationResult<IntegerToken> result = TOKENIZER.tokenize(command);
    assertNotNull(result.getToken());
    assertEquals(command.replace("1234", ""), result.getRemainingCommand());
  }

  @Test
  public void testBuildTokenWithOverflowingNumber() {
    String value = "12345678901234567890";
    TokenizationResult<IntegerToken> result = TOKENIZER.tokenize(value);
    assertEquals(IntegerToken.forValue(value), result.getToken());
    assertTrue(result.getRemainingCommand().isEmpty());
  }

}