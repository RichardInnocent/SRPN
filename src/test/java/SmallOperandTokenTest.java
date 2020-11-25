import static org.junit.Assert.*;

import org.junit.Test;

public class SmallOperandTokenTest {

  @Test
  public void testCanGenerateTokenFromInteger() {
    int value = 12345;
    OperandToken token = SmallOperandToken.forValue(value);
    assertEquals(Integer.toString(value), token.getReadableValue());
  }

  @Test
  public void testCanGenerateTokenFromText() {
    String value = "12345";
    OperandToken token = SmallOperandToken.forValue(value);
    assertEquals(value, token.getReadableValue());
  }

  @Test(expected = NumberFormatException.class)
  public void testExceptionThrownIfTextIsNotANumber() {
    SmallOperandToken.forValue("Clearly not a number");
  }

  @Test(expected = NumberFormatException.class)
  public void testExceptionThrownIfValueTooLarge() {
    SmallOperandToken.forValue("12345678901234567890");
  }

  @Test
  public void testEqualsReturnsTrueIfComparedAgainstMatchingSmallIntegerToken() {
    int value = 4;
    SmallOperandToken token1 = SmallOperandToken.forValue(value);
    SmallOperandToken token2 = SmallOperandToken.forValue(value);
    assertTrue(token1.equals(token2));
    assertTrue(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsFalseIfComparedAgainstDifferentMatchingSmallIntegerToken() {
    SmallOperandToken token1 = SmallOperandToken.forValue(4);
    SmallOperandToken token2 = SmallOperandToken.forValue(5);
    assertFalse(token1.equals(token2));
    assertFalse(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsTrueIfComparedAgainstAnotherIntegerTokenOfDifferentTypeButSameValue() {
    int value = 4;
    SmallOperandToken token1 = SmallOperandToken.forValue(value);
    OperandToken token2 = BigOperandToken.forValue(Integer.toString(value));
    assertTrue(token1.equals(token2));
  }

  @Test
  public void testEqualsReturnsFalseIfComparedAgainstAnotherIntegerTokenOfDifferentTypeAndValue() {
    SmallOperandToken token1 = SmallOperandToken.forValue(4);
    OperandToken token2 = BigOperandToken.forValue("5");
    assertFalse(token1.equals(token2));
  }

  @Test
  public void testFlipSignOfNormalPositiveValue() {
    int value = 4;
    SmallOperandToken originalToken = SmallOperandToken.forValue(value);
    OperandToken flippedToken = originalToken.flipSign();
    assertEquals(SmallOperandToken.forValue(-value), flippedToken);
  }

  @Test
  public void testFlipSignOfNormalNegativeValue() {
    int value = -4;
    SmallOperandToken originalToken = SmallOperandToken.forValue(value);
    OperandToken flippedToken = originalToken.flipSign();
    assertEquals(SmallOperandToken.forValue(-value), flippedToken);
  }

  @Test
  public void testFlipSignOfMaxValue() {
    SmallOperandToken originalToken = SmallOperandToken.forValue(Integer.MAX_VALUE);
    OperandToken flippedToken = originalToken.flipSign();
    assertEquals(SmallOperandToken.forValue(-Integer.MAX_VALUE), flippedToken);
  }

  @Test
  public void testFlipSignOfMinValue() {
    SmallOperandToken originalToken = SmallOperandToken.forValue(Integer.MIN_VALUE);
    OperandToken flippedToken = originalToken.flipSign();
    assertEquals(
        BigOperandToken.forValue(Integer.toString(Integer.MIN_VALUE).substring(1)),
        flippedToken
    );
  }

  @Test
  public void testTruncatedIntValue() {
    assertEquals(4, SmallOperandToken.forValue(4).truncateDoubleToIntBounds());
    assertEquals(-4, SmallOperandToken.forValue(-4).truncateDoubleToIntBounds());
    assertEquals(0, SmallOperandToken.forValue(0).truncateDoubleToIntBounds());
    assertEquals(
        Integer.MAX_VALUE,
        SmallOperandToken.forValue(Integer.MAX_VALUE).truncateDoubleToIntBounds()
    );
    assertEquals(
        Integer.MIN_VALUE,
        SmallOperandToken.forValue(Integer.MIN_VALUE).truncateDoubleToIntBounds()
    );
  }

}