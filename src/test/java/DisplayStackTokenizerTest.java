import static org.junit.Assert.*;

import java.util.Optional;
import org.junit.Test;

public class DisplayStackTokenizerTest {

  private final DisplayStackTokenizer tokenizer = new DisplayStackTokenizer();

  @Test
  public void testBuildValidToken() {
    assertTrue(tokenizer.buildToken('d').isPresent());
  }

  @Test
  public void testBuildInvalidToken() {
    assertFalse(tokenizer.buildToken('D').isPresent());
  }

}