import static org.junit.Assert.*;

import org.junit.Test;

public class RandomNumberTokenizerTest {

  private static final Tokenizer<RandomNumberToken> TOKENIZER = new RandomNumberTokenizer();

  @Test
  public void testCanTokenize() {
    TokenizerTestUtils.assertTokenizerCanOnlyTokenize(TOKENIZER, 'r');
  }

  @Test
  public void testBuildToken() {
    String command = "A command";
    TokenizationResult<RandomNumberToken> result = TOKENIZER.tokenize(command);
    assertEquals(command.substring(1), result.getRemainingCommand());
  }

}