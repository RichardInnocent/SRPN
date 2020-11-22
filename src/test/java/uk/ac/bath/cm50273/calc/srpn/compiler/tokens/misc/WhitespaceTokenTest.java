package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.Token;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.AdditionToken;

public class WhitespaceTokenTest {

  @Test
  public void testHashCode() {
    assertEquals("whitespace".hashCode(), new WhitespaceToken().hashCode());
  }

  @Test
  public void testEquals() {
    Token whitespaceToken1 = new WhitespaceToken();
    Token whitespaceToken2 = new WhitespaceToken();
    Token otherToken = new AdditionToken();

    assertTrue(whitespaceToken1.equals(whitespaceToken2));
    assertTrue(whitespaceToken2.equals(whitespaceToken1));
    assertFalse(whitespaceToken1.equals(otherToken));
  }

}