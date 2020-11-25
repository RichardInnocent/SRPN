import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SubtractionOperatorTest {

  private static final Operator OPERATOR = new SubtractionOperator();

  @Test
  public void testBasicSubtractionOfPositiveNumbers() {
    int value1 = 12;
    int value2 = 15;
    assertEquals(value1 - value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicSubtractionOfOnePositiveAndOneNegativeNumber() {
    int value1 = 12;
    int value2 = -15;
    assertEquals(value1 - value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicSubtractionOfOneNegativeAndOnePositiveNumber() {
    int value1 = -12;
    int value2 = 15;
    assertEquals(value1 - value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicSubtractionNegativeNumbers() {
    int value1 = -12;
    int value2 = -15;
    assertEquals(value1 - value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testSubtractionOfLargePositiveValuesFromLargeNegativeNumberTruncates() {
    assertEquals(Integer.MIN_VALUE, OPERATOR.apply(Integer.MIN_VALUE, Integer.MAX_VALUE));
  }

  @Test
  public void testSubtractionOfLargeNegativeFromLargePositiveNumberTruncates() {
    assertEquals(Integer.MAX_VALUE, OPERATOR.apply(Integer.MAX_VALUE, Integer.MIN_VALUE));
  }

}