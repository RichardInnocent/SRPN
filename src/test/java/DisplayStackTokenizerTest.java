import static org.junit.Assert.*;

import java.util.Optional;
import org.junit.Test;

public class DisplayStackTokenizerTest {

  private final DisplayStackTokenizer tokenizer = new DisplayStackTokenizer();

  @Test
  public void testBuildValidToken() {
    Optional<DisplayStackToken> token = tokenizer.buildToken('d');
    assertTrue(token.isPresent());
  }

  @Test
  public void testBuildInvalidToken() {
    Optional<DisplayStackToken> token = tokenizer.buildToken('D');
    assertFalse(token.isPresent());
  }

}