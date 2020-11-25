import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class TokenizationResultTest {

  @Test
  public void testForTokenAndRemainingCommandSetsFieldsCorrectly() {
    Token token = mock(Token.class);
    String remainingCommand = "Remaining command";

    TokenizationResult<Token> result =
        TokenizationResult.forTokenAndRemainingCommand(token, remainingCommand);

    assertEquals(token, result.getToken());
    assertEquals(remainingCommand, result.getRemainingCommand());
  }

  @Test(expected = NullPointerException.class)
  public void testForTokenAndRemainingCommandThrowsExceptionIfTokenIsNull() {
    TokenizationResult.forTokenAndRemainingCommand(null, "Remaining command");
  }

  @Test(expected = NullPointerException.class)
  public void testForTokenAndRemainingCommandThrowsExceptionIfRemainingCommandIsNull() {
    TokenizationResult.forTokenAndRemainingCommand(mock(Token.class), null);
  }

}