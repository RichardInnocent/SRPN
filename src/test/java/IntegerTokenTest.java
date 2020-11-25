import static org.junit.Assert.*;

import org.junit.Test;

public class IntegerTokenTest {

  @Test
  public void testCanCreateTokenFromInteger() {
    int value = 4;
    IntegerToken token = IntegerToken.forValue(value);
    assertEquals(Integer.toString(value), token.getReadableValue());
    assertTrue(token instanceof SmallIntegerToken);
  }

  @Test
  public void testCanCreateSmallTokenFromString() {
    String value = "12345";
    IntegerToken token = IntegerToken.forValue(value);
    assertEquals(value, token.getReadableValue());
    assertTrue(token instanceof SmallIntegerToken);
  }

  @Test
  public void testCanCreateLargeTokenFromString() {
    String value = "1234567890123456789012345678901234567890";
    IntegerToken token = IntegerToken.forValue(value);
    assertEquals(value, token.getReadableValue());
    assertTrue(token instanceof BigIntegerToken);
  }

  @Test(expected = NumberFormatException.class)
  public void testExceptionThrownIfAttemptingToCreateTokenFromNonNumber() {
    IntegerToken.forValue("Not numeric");
  }

  @Test
  public void test120CharacterLongIntegerCanBeTokenized() {
    String value = "9".repeat(120);
    IntegerToken token = IntegerToken.forValue("9".repeat(120));
    assertEquals(value, token.getReadableValue());
  }

  @Test(expected = DummySegmentationFaultException.class)
  public void test121CharacterLongIntegerCannotBeTokenized() {
    IntegerToken.forValue("1" + "0".repeat(120));
  }

  @Test
  public void testEqualsReturnsTrueWhenComparingIdenticalSmallIntegerTokens() {
    int value = 7;
    IntegerToken token1 = IntegerToken.forValue(value);
    IntegerToken token2 = IntegerToken.forValue(value);
    assertTrue(token1.equals(token2));
    assertTrue(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsFalseWhenComparingDifferentSmallIntegerTokens() {
    IntegerToken token1 = IntegerToken.forValue(7);
    IntegerToken token2 = IntegerToken.forValue(8);
    assertFalse(token1.equals(token2));
    assertFalse(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsTrueWhenComparingIdenticalBigIntegerTokens() {
    String value = "123456789012345678901234567890";
    IntegerToken token1 = IntegerToken.forValue(value);
    IntegerToken token2 = IntegerToken.forValue(value);
    assertTrue(token1.equals(token2));
    assertTrue(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsFalseWhenComparingDifferentBigIntegerTokens() {
    IntegerToken token1 = IntegerToken.forValue("123456789012345678901234567890");
    IntegerToken token2 = IntegerToken.forValue("123456789012345678901234567891");
    assertFalse(token1.equals(token2));
    assertFalse(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsFalseWhenComparingEqualTokensOfDifferentType() {
    String value = "8";
    IntegerToken smallToken = SmallIntegerToken.forValue(value);
    IntegerToken bigToken = BigIntegerToken.forValue(value);
    assertTrue(smallToken.equals(bigToken));
    assertTrue(bigToken.equals(smallToken)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsFalseWhenComparingTokensOfDifferentTypeAndValue() {
    IntegerToken smallToken = SmallIntegerToken.forValue("8");
    IntegerToken bigToken = BigIntegerToken.forValue("9");
    assertFalse(smallToken.equals(bigToken));
    assertFalse(bigToken.equals(smallToken)); // Ensure symmetric
  }

}