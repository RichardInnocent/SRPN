import static org.junit.Assert.*;

import org.junit.Test;

public class EqualsTokenizerTest {

  private static final EqualsTokenizer TOKENIZER = new EqualsTokenizer();

  @Test
  public void testBuildValidToken() {
    assertTrue(TOKENIZER.buildToken('=').isPresent());
  }

  @Test
  public void testBuildInvalidToken() {
    assertFalse(TOKENIZER.buildToken('a').isPresent());
  }

}