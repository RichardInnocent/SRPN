import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BigIntegerTokenTest {

  @Test
  public void testCanGenerateSmallTokenFromText() {
    String value = "12345";
    IntegerToken token = BigIntegerToken.forValue(value);
    assertEquals(value, token.getReadableValue());
  }

  @Test
  public void testCanGenerateHugeTokenFromText() {
    String value = "123456789012345678901234567890123456789012345678901234567890";
    IntegerToken token = BigIntegerToken.forValue(value);
    assertEquals(value, token.getReadableValue());
  }

  @Test(expected = NumberFormatException.class)
  public void testExceptionThrownIfTextIsNotANumber() {
    BigIntegerToken.forValue("Clearly not a number");
  }

  @Test
  public void test120CharacterLongIntegerCanBeTokenized() {
    String value = "9".repeat(120);
    IntegerToken token = BigIntegerToken.forValue("9".repeat(120));
    assertEquals(value, token.getReadableValue());
  }

  @Test(expected = DummySegmentationFaultException.class)
  public void test121CharacterLongIntegerCannotBeTokenized() {
    BigIntegerToken.forValue("1" + "0".repeat(120));
  }

  @Test
  public void testFlipSignOfStandardPositiveValue() {
    String value = "12";
    IntegerToken token = BigIntegerToken.forValue(value).flipSign();
    assertEquals(BigIntegerToken.forValue("-" + value), token);
  }

  @Test
  public void testFlipSignOfStandardNegativeValue() {
    String value = "-12";
    IntegerToken token = BigIntegerToken.forValue(value).flipSign();
    assertEquals(BigIntegerToken.forValue(value.substring(1)), token);
  }

  @Test
  public void testFlipSignOf119CharacterLongPositiveValue() {
    String value = "9".repeat(119);
    IntegerToken token = BigIntegerToken.forValue(value).flipSign();
    assertEquals(BigIntegerToken.forValue("-" + value), token);
  }

  @Test(expected = DummySegmentationFaultException.class)
  public void testFlipSignOf120CharacterLongPositiveValueThrowsException() {
    BigIntegerToken.forValue("1" + "0".repeat(119)).flipSign();
  }

  @Test
  public void testFlipSignOf120CharacterLongNegativeValue() {
    String value = "-" + "9".repeat(119);
    IntegerToken token = BigIntegerToken.forValue(value).flipSign();
    assertEquals(BigIntegerToken.forValue(value.substring(1)), token);
  }

  @Test
  public void testTruncatedIntValue() {
    assertEquals(4, BigIntegerToken.forValue(4).truncatedIntValue());
    assertEquals(-4, BigIntegerToken.forValue(-4).truncatedIntValue());
    assertEquals(0, BigIntegerToken.forValue(0).truncatedIntValue());
    assertEquals(
        Integer.MAX_VALUE,
        BigIntegerToken.forValue(Long.toString(Long.MAX_VALUE)).truncatedIntValue()
    );
    assertEquals(
        Integer.MIN_VALUE,
        BigIntegerToken.forValue(Long.toString(Long.MIN_VALUE)).truncatedIntValue()
    );
  }

}