import static org.junit.Assert.*;

import org.junit.Test;

public class RandomNumberTokenizerTest {

  private static final RandomNumberTokenizer TOKENIZER = new RandomNumberTokenizer();

  @Test
  public void testCanTokenizeLowercaseR() {
    assertTrue(TOKENIZER.buildToken('r').isPresent());
  }

  @Test
  public void testCannotTokenizeOtherCharacters() {
    assertFalse(TOKENIZER.buildToken('R').isPresent());
  }

}