import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SubtractionOperatorTest {

  private static final double DELTA = 1e-8;
  private static final Operator OPERATOR = new SubtractionOperator();

  @Test
  public void testBasicSubtractionOfPositiveNumbers() {
    int value1 = 12;
    int value2 = 15;
    assertEquals(value1 - value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testBasicSubtractionOfOnePositiveAndOneNegativeNumber() {
    int value1 = 12;
    int value2 = -15;
    assertEquals(value1 - value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testBasicSubtractionOfOneNegativeAndOnePositiveNumber() {
    int value1 = -12;
    int value2 = 15;
    assertEquals(value1 - value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testBasicSubtractionNegativeNumbers() {
    int value1 = -12;
    int value2 = -15;
    assertEquals(value1 - value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testSubtractionOfLargePositiveValuesFromLargeNegativeNumberTruncates() {
    assertEquals(
        Integer.MIN_VALUE, OPERATOR.calculate(Integer.MIN_VALUE, Integer.MAX_VALUE), DELTA
    );
  }

  @Test
  public void testSubtractionOfLargeNegativeFromLargePositiveNumberTruncates() {
    assertEquals(
        Integer.MAX_VALUE, OPERATOR.calculate(Integer.MAX_VALUE, Integer.MIN_VALUE), DELTA
    );
  }

}