package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.Token;

public class TokenizationResultTest {

  @Test
  public void testEqualsAndHashCode() {
    EqualsVerifier.forClass(TokenizationResult.class).suppress(Warning.STRICT_INHERITANCE).verify();
  }

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