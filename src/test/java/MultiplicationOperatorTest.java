import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MultiplicationOperatorTest {

  private static final double DELTA = 1e-8;
  private static final Operator OPERATOR = new MultiplicationOperator();

  @Test
  public void testBasicMultiplicationOfPositiveNumbers() {
    int value1 = 12;
    int value2 = 15;
    assertEquals(value1 * value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testBasicMultiplicationOfOnePositiveAndOneNegativeNumber() {
    int value1 = 12;
    int value2 = -15;
    assertEquals(value1 * value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testBasicMultiplicationOfOneNegativeAndOnePositiveNumber() {
    int value1 = -12;
    int value2 = 15;
    assertEquals(value1 * value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testBasicMultiplicationOfNegativeNumbers() {
    int value1 = -12;
    int value2 = -15;
    assertEquals(value1 * value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testMultiplicationOfLargePositiveValuesTruncates() {
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(Integer.MAX_VALUE, Integer.MAX_VALUE), DELTA);
  }

  @Test
  public void testMultiplicationOfLargeNegativeValuesTruncates() {
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(Integer.MIN_VALUE, Integer.MIN_VALUE), DELTA);
  }

  @Test
  public void testMultiplicationOfLargePositiveNumberAndLargeNegativeNumberTruncates() {
    assertEquals(Integer.MIN_VALUE, OPERATOR.calculate(Integer.MAX_VALUE, Integer.MIN_VALUE), DELTA);
  }

}