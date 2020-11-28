import static org.junit.Assert.*;

import java.math.BigInteger;
import org.junit.Test;

public class OperandTokenTest {

  @Test
  public void testCanCreateTokenFromInteger() {
    int value = 4;
    OperandToken token = OperandToken.forValue(value);
    assertEquals(Integer.toString(value), token.getReadableValue());
    assertTrue(token instanceof SmallOperandToken);
  }

  @Test
  public void testCanCreateSmallTokenFromString() {
    String value = "12345";
    OperandToken token = OperandToken.forValue(value, 10);
    assertEquals(value, token.getReadableValue());
    assertTrue(token instanceof SmallOperandToken);
  }

  @Test
  public void testCanCreateLargeTokenFromString() {
    String value = "1234567890123456789012345678901234567890";
    OperandToken token = OperandToken.forValue(value, 10);
    assertEquals(value, token.getReadableValue());
    assertTrue(token instanceof BigOperandToken);
  }

  @Test
  public void testSmallNumberInOctal() {
    String value = "01562";
    OperandToken token = OperandToken.forValue(value, 8);
    assertEquals(Integer.parseInt(value, 8), token.intValue());
    assertTrue(token instanceof SmallOperandToken);
  }

  @Test
  public void testLargeNumberInAsOctal() {
    String value = "012345670123456701234567012345670";
    OperandToken token = OperandToken.forValue(value, 10);
    assertEquals(new BigInteger(value, 8).toString(), token.getReadableValue());
    assertTrue(token instanceof BigOperandToken);
  }

  @Test(expected = NumberFormatException.class)
  public void testExceptionThrownIfAttemptingToCreateTokenFromNonNumber() {
    OperandToken.forValue("Not numeric", 10);
  }

  @Test
  public void test120CharacterLongIntegerCanBeTokenized() {
    String value = "9".repeat(120);
    OperandToken token = OperandToken.forValue("9".repeat(120), 10);
    assertEquals(value, token.getReadableValue());
  }

  @Test(expected = DummySegmentationFaultException.class)
  public void test121CharacterLongIntegerCannotBeTokenized() {
    OperandToken.forValue("1" + "0".repeat(120), 10);
  }

  @Test
  public void testEqualsReturnsTrueWhenComparingIdenticalSmallIntegerTokens() {
    int value = 7;
    OperandToken token1 = OperandToken.forValue(value);
    OperandToken token2 = OperandToken.forValue(value);
    assertTrue(token1.equals(token2));
    assertTrue(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsFalseWhenComparingDifferentSmallIntegerTokens() {
    OperandToken token1 = OperandToken.forValue(7);
    OperandToken token2 = OperandToken.forValue(8);
    assertFalse(token1.equals(token2));
    assertFalse(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsTrueWhenComparingIdenticalBigIntegerTokens() {
    String value = "123456789012345678901234567890";
    OperandToken token1 = OperandToken.forValue(value, 10);
    OperandToken token2 = OperandToken.forValue(value, 10);
    assertTrue(token1.equals(token2));
    assertTrue(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsFalseWhenComparingDifferentBigIntegerTokens() {
    OperandToken token1 = OperandToken.forValue("123456789012345678901234567890", 10);
    OperandToken token2 = OperandToken.forValue("123456789012345678901234567891", 10);
    assertFalse(token1.equals(token2));
    assertFalse(token2.equals(token1)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsFalseWhenComparingEqualTokensOfDifferentType() {
    String value = "8";
    OperandToken smallToken = SmallOperandToken.forValue(value, 10);
    OperandToken bigToken = BigOperandToken.forValue(value, 10);
    assertTrue(smallToken.equals(bigToken));
    assertTrue(bigToken.equals(smallToken)); // Ensure symmetric
  }

  @Test
  public void testEqualsReturnsFalseWhenComparingTokensOfDifferentTypeAndValue() {
    OperandToken smallToken = SmallOperandToken.forValue("8", 10);
    OperandToken bigToken = BigOperandToken.forValue("9", 10);
    assertFalse(smallToken.equals(bigToken));
    assertFalse(bigToken.equals(smallToken)); // Ensure symmetric
  }

}