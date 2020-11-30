import static org.junit.Assert.*;

import org.junit.Test;

public class TokenGroupTest {

  @Test
  public void testAddingTokens() {
    Token token1 = OperandToken.forValue(1);
    Token token2 = new ModuloToken();
    Token token3 = OperandToken.forValue(65);

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
    group.addAndTidy(OperandToken.forValue(3));
    assertFalse(group.isEmpty());
    assertEquals(1, group.size());
    assertEquals(OperandToken.forValue(-3), group.getToken(0));
  }

  @Test
  public void testAddingNumericTokenAfterTwoMinusSignsAtStartDoesNotFlipValue() {
    TokenGroup group = new TokenGroup();
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(OperandToken.forValue(3));
    assertEquals(3, group.size());
    assertTrue(group.getToken(0) instanceof SubtractionToken);
    assertTrue(group.getToken(1) instanceof SubtractionToken);
    assertEquals(OperandToken.forValue(3), group.getToken(2));
  }

  @Test
  public void testAddingNumericTokenAfterThreeMinusAtStartSignsDoesFlipValue() {
    TokenGroup group = new TokenGroup();
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(OperandToken.forValue(3));
    assertEquals(3, group.size());
    assertTrue(group.getToken(0) instanceof SubtractionToken);
    assertTrue(group.getToken(1) instanceof SubtractionToken);
    assertEquals(OperandToken.forValue(-3), group.getToken(2));
  }

  @Test
  public void testAddingNumericTokenAfterTwoMinusSignsAfterOperatorDoesNotFlipValue() {
    TokenGroup group = new TokenGroup();
    group.addAndTidy(new AdditionToken());
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(OperandToken.forValue(3));
    assertEquals(4, group.size());
    assertTrue(group.getToken(0) instanceof AdditionToken);
    assertTrue(group.getToken(1) instanceof SubtractionToken);
    assertTrue(group.getToken(2) instanceof SubtractionToken);
    assertEquals(OperandToken.forValue(3), group.getToken(3));
  }

  @Test
  public void testAddingNumericTokenAfterThreeMinusSignsAfterOperatorDoesNotFlipValue() {
    TokenGroup group = new TokenGroup();
    group.addAndTidy(new AdditionToken());
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(OperandToken.forValue(3));
    assertEquals(4, group.size());
    assertTrue(group.getToken(0) instanceof AdditionToken);
    assertTrue(group.getToken(1) instanceof SubtractionToken);
    assertTrue(group.getToken(2) instanceof SubtractionToken);
    assertEquals(OperandToken.forValue(-3), group.getToken(3));
  }

  @Test
  public void testAddingNumericTokenAfterTwoMinusSignsAfterOperandDoesNotFlipValue() {
    TokenGroup group = new TokenGroup();
    group.addAndTidy(OperandToken.forValue(1));
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(OperandToken.forValue(3));
    assertEquals(4, group.size());
    assertEquals(OperandToken.forValue(1), group.getToken(0));
    assertTrue(group.getToken(1) instanceof SubtractionToken);
    assertTrue(group.getToken(2) instanceof SubtractionToken);
    assertEquals(OperandToken.forValue(3), group.getToken(3));
  }

  @Test
  public void testAddingNumericTokenAfterThreeMinusSignsAfterOperandDoesNotFlipValue() {
    TokenGroup group = new TokenGroup();
    group.addAndTidy(OperandToken.forValue(1));
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(new SubtractionToken());
    group.addAndTidy(OperandToken.forValue(3));
    assertEquals(5, group.size());
    assertEquals(OperandToken.forValue(1), group.getToken(0));
    assertTrue(group.getToken(1) instanceof SubtractionToken);
    assertTrue(group.getToken(2) instanceof SubtractionToken);
    assertTrue(group.getToken(3) instanceof SubtractionToken);
    assertEquals(OperandToken.forValue(3), group.getToken(4));
  }

}