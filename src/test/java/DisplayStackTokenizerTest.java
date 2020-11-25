import static org.junit.Assert.*;

import org.junit.Test;

public class DisplayStackTokenizerTest {

  private static final Tokenizer<DisplayStackToken> TOKENIZER = new DislayStackTokenizer();

  @Test
  public void testCanTokenize() {
    TokenizerTestUtils.assertTokenizerCanOnlyTokenize(TOKENIZER, 'd');
  }

  @Test
  public void testBuildToken() {
    String command = "A command";
    TokenizationResult<DisplayStackToken> result = TOKENIZER.tokenize(command);
    assertNotNull(result.getToken());
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

}