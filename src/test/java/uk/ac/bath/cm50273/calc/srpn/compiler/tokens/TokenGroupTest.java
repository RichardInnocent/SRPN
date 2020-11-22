package uk.ac.bath.cm50273.calc.srpn.compiler.tokens;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric.IntegerToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.ModuloToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator.SubtractionToken;

public class TokenGroupTest {

  @Test
  public void testAddingTokens() {
    Token token1 = IntegerToken.forValue(1);
    Token token2 = new ModuloToken();
    Token token3 = IntegerToken.forValue(65);

    TokenGroup group = new TokenGroup();
    group.addAndTidy(token1);
    group.addAndTidy(token2);
    group.addAndTidy(token3);

    assertFalse(group.isEmpty());
    assertEquals(3, group.size());
    assertEquals(token1, group.getToken(0));
    assertEquals(token2, group.getToken(1));
    assertEquals(token3, group.getToken(2));
  }

  @Test
  public void testAddingNumericTokenAfterPositiveNumericTokenTidiesToSingleToken() {
    TokenGroup group = new TokenGroup();
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(IntegerToken.forValue(3));
    assertFalse(group.isEmpty());
    assertEquals(1, group.size());
    assertEquals(IntegerToken.forValue(-3), group.getToken(0));
  }

}