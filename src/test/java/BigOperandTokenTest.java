import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BigOperandTokenTest {

  @Test
  public void testCanGenerateSmallTokenFromText() {
    String value = "12345";
    OperandToken token = BigOperandToken.forValue(value, 10);
    assertEquals(value, token.getReadableValue());
  }

  @Test
  public void testCanGenerateHugeTokenFromText() {
    String value = "123456789012345678901234567890123456789012345678901234567890";
    OperandToken token = BigOperandToken.forValue(value, 10);
    assertEquals(value, token.getReadableValue());
  }

  @Test(expected = NumberFormatException.class)
  public void testExceptionThrownIfTextIsNotANumber() {
    BigOperandToken.forValue("Clearly not a number", 10);
  }

  @Test
  public void test120CharacterLongIntegerCanBeTokenized() {
    String value = "9".repeat(120);
    OperandToken token = BigOperandToken.forValue("9".repeat(120), 10);
    assertEquals(value, token.getReadableValue());
  }

  @Test(expected = DummySegmentationFaultException.class)
  public void test121CharacterLongIntegerCannotBeTokenized() {
    BigOperandToken.forValue("1" + "0".repeat(120), 10);
  }

  @Test
  public void testFlipSignOfStandardPositiveValue() {
    String value = "12";
    OperandToken token = BigOperandToken.forValue(value, 10).flipSign();
    assertEquals(BigOperandToken.forValue("-" + value, 10), token);
  }

  @Test
  public void testFlipSignOfStandardNegativeValue() {
    String value = "-12";
    OperandToken token = BigOperandToken.forValue(value, 10).flipSign();
    assertEquals(BigOperandToken.forValue(value.substring(1), 10), token);
  }

  @Test
  public void testFlipSignOf119CharacterLongPositiveValue() {
    String value = "9".repeat(119);
    OperandToken token = BigOperandToken.forValue(value, 10).flipSign();
    assertEquals(BigOperandToken.forValue("-" + value, 10), token);
  }

  @Test(expected = DummySegmentationFaultException.class)
  public void testFlipSignOf120CharacterLongPositiveValueThrowsException() {
    BigOperandToken.forValue("1" + "0".repeat(119), 10).flipSign();
  }

  @Test
  public void testFlipSignOf120CharacterLongNegativeValue() {
    String value = "-" + "9".repeat(119);
    OperandToken token = BigOperandToken.forValue(value, 10).flipSign();
    assertEquals(BigOperandToken.forValue(value.substring(1), 10), token);
  }

  @Test
  public void testTruncatedIntValue() {
    assertEquals(4, BigOperandToken.forValue(4).intValue());
    assertEquals(-4, BigOperandToken.forValue(-4).intValue());
    assertEquals(0, BigOperandToken.forValue(0).intValue());
    assertEquals(
        Integer.MAX_VALUE,
        BigOperandToken.forValue(Long.toString(Long.MAX_VALUE), 10).intValue()
    );
    assertEquals(
        Integer.MIN_VALUE,
        BigOperandToken.forValue(Long.toString(Long.MIN_VALUE), 10).intValue()
    );
  }

}