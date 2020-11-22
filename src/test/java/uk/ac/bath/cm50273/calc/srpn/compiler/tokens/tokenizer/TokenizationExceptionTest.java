package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class TokenizationExceptionTest {

  @Test
  public void testMessageConstructor() {
    String message = "Test message";
    assertEquals(message, new TokenizationException(message).getMessage());
  }

  @Test
  public void testMessageAndCauseConstructor() {
    String message = "Test message";
    Throwable cause = mock(Throwable.class);

    Throwable exception = new TokenizationException(message, cause);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

}