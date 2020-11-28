import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RaiseToPowerOperatorTest {

  private static final Operator OPERATOR = new RaiseToPowerOperator();
  private static final double DELTA = 1e-8;

  @Test
  public void testRaisingSmallNumbersToPower() {
    int base = 20;
    int exponent = 4;
    assertEquals(Math.pow(base, exponent), OPERATOR.calculate(base, exponent), DELTA);
  }

  @Test
  public void testRaisingZeroToAnyPowerReturnsZero() {
    assertEquals(0d, OPERATOR.calculate(0d, 20d), DELTA);
    assertEquals(0d, OPERATOR.calculate(0d, Integer.MAX_VALUE), DELTA);
  }

  @Test(expected = NegativePowerException.class)
  public void testRaisingZeroToAnyNegativePowerThrowsException() {
    OPERATOR.calculate(0d, Integer.MIN_VALUE);
  }

  @Test
  public void testRaisingOneToAnyPositivePowerReturnsOne() {
    assertEquals(1d, OPERATOR.calculate(1d, 20d), DELTA);
    assertEquals(1d, OPERATOR.calculate(1d, Integer.MAX_VALUE), DELTA);
  }

  @Test(expected = NegativePowerException.class)
  public void testRaisingOneToAnyNegativePowerThrowsException() {
    OPERATOR.calculate(1d, Integer.MIN_VALUE);
  }

  @Test
  public void testRaisingNumberWithinBoundsToPowerOfOneReturnsNumber() {
    assertEquals(20d, OPERATOR.calculate(20d, 1d), DELTA);
    assertEquals(-20d, OPERATOR.calculate(-20d, 1d), DELTA);
  }

  @Test
  public void testRaisingToPowerOf2() {
    assertEquals(9d, OPERATOR.calculate(3d, 2d), DELTA);
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(Integer.MAX_VALUE, 2d), DELTA);
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(Integer.MIN_VALUE, 2d), DELTA);
  }

  @Test
  public void testRaisingToPowerOfOrGreaterThan31ReturnsTruncatedValue() {
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(2d, 31d), DELTA);
    assertEquals(Integer.MIN_VALUE, OPERATOR.calculate(-2d, 31d), DELTA);
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(2d, 32d), DELTA);
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(-2d, 32d), DELTA);
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(-76d, 10356d), DELTA);
    assertEquals(Integer.MIN_VALUE, OPERATOR.calculate(-76d, 10355d), DELTA);
  }

  @Test
  public void testRaisingToPowerLessThan31ReturnsTruncatedValue() {
    assertEquals((int) Math.pow(-3d, 15d), OPERATOR.calculate(-3d, 15d), DELTA);
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(Integer.MAX_VALUE, 30d), DELTA);
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(Integer.MIN_VALUE, 30d), DELTA);
    assertEquals(Integer.MIN_VALUE, OPERATOR.calculate(Integer.MIN_VALUE, 29d), DELTA);
  }

  @Test
  public void testRaisingFractionToLargePower() {
    double operand1 = 999_999d / 1_000_000d;
    double operand2 = 2000d;
    assertEquals(0.9980019967, OPERATOR.calculate(operand1, operand2), DELTA);
  }

  @Test
  public void testRaisingFractionToVeryLargePower() {
    double operand1 = 999_999_999d / 1_000_000_000d;
    double operand2 = Integer.MAX_VALUE;
    assertEquals(0.1167776491, OPERATOR.calculate(operand1, operand2), DELTA);
  }

}