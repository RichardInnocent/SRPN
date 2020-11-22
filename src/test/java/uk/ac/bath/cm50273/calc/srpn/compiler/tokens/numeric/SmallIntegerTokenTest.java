package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmallIntegerTokenTest {

  @Test
  public void testCanGenerateTokenFromInteger() {
    int value = 12345;
    IntegerToken token = SmallIntegerToken.forValue(value);
    assertEquals(Integer.toString(value), token.getReadableValue());
  }

  @Test
  public void testCanGenerateTokenFromText() {
    String value = "12345";
    IntegerToken token = SmallIntegerToken.forValue(value);
    assertEquals(value, token.getReadableValue());
  }

  @Test(expected = NumberFormatException.class)
  public void testExceptionThrownIfTextIsNotANumber() {
    SmallIntegerToken.forValue("Clearly not a number");
  }

  @Test(expected = NumberFormatException.class)
  public void testExceptionThrownIfValueTooLarge() {
    SmallIntegerToken.forValue("12345678901234567890");
  }

  @Test
  public void testEqualsReturnsTrueIfComparedAgainstMatchingSmallIntegerToken() {
    int value = 4;
    SmallIntegerToken token1 = SmallIntegerToken.forValue(value);
    SmallIntegerToken token2 = SmallIntegerToken.forValue(value);
    assertTrue(token1.equals(token2));
    assertTrue(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsFalseIfComparedAgainstDifferentMatchingSmallIntegerToken() {
    SmallIntegerToken token1 = SmallIntegerToken.forValue(4);
    SmallIntegerToken token2 = SmallIntegerToken.forValue(5);
    assertFalse(token1.equals(token2));
    assertFalse(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsTrueIfComparedAgainstAnotherIntegerTokenOfDifferentTypeButSameValue() {
    int value = 4;
    SmallIntegerToken token1 = SmallIntegerToken.forValue(value);
    IntegerToken token2 = BigIntegerToken.forValue(Integer.toString(value));
    assertTrue(token1.equals(token2));
  }

  @Test
  public void testEqualsReturnsFalseIfComparedAgainstAnotherIntegerTokenOfDifferentTypeAndValue() {
    SmallIntegerToken token1 = SmallIntegerToken.forValue(4);
    IntegerToken token2 = BigIntegerToken.forValue("5");
    assertFalse(token1.equals(token2));
  }

  @Test
  public void testFlipSignOfNormalPositiveValue() {
    int value = 4;
    SmallIntegerToken originalToken = SmallIntegerToken.forValue(value);
    IntegerToken flippedToken = originalToken.flipSign();
    assertEquals(SmallIntegerToken.forValue(-value), flippedToken);
  }

  @Test
  public void testFlipSignOfNormalNegativeValue() {
    int value = -4;
    SmallIntegerToken originalToken = SmallIntegerToken.forValue(value);
    IntegerToken flippedToken = originalToken.flipSign();
    assertEquals(SmallIntegerToken.forValue(-value), flippedToken);
  }

  @Test
  public void testFlipSignOfMaxValue() {
    SmallIntegerToken originalToken = SmallIntegerToken.forValue(Integer.MAX_VALUE);
    IntegerToken flippedToken = originalToken.flipSign();
    assertEquals(SmallIntegerToken.forValue(-Integer.MAX_VALUE), flippedToken);
  }

  @Test
  public void testFlipSignOfMinValue() {
    SmallIntegerToken originalToken = SmallIntegerToken.forValue(Integer.MIN_VALUE);
    IntegerToken flippedToken = originalToken.flipSign();
    assertEquals(
        BigIntegerToken.forValue(Integer.toString(Integer.MIN_VALUE).substring(1)),
        flippedToken
    );
  }

  @Test
  public void testTruncatedIntValue() {
    assertEquals(4, SmallIntegerToken.forValue(4).truncatedIntValue());
    assertEquals(-4, SmallIntegerToken.forValue(-4).truncatedIntValue());
    assertEquals(0, SmallIntegerToken.forValue(0).truncatedIntValue());
    assertEquals(
        Integer.MAX_VALUE,
        SmallIntegerToken.forValue(Integer.MAX_VALUE).truncatedIntValue()
    );
    assertEquals(
        Integer.MIN_VALUE,
        SmallIntegerToken.forValue(Integer.MIN_VALUE).truncatedIntValue()
    );
  }

}