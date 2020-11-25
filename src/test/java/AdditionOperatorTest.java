import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AdditionOperatorTest {

  private static final Operator OPERATOR = new AdditionOperator();

  @Test
  public void testBasicAdditionOfPositiveNumbers() {
    int value1 = 12;
    int value2 = 15;
    assertEquals(value1 + value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicAdditionOfOnePositiveAndOneNegativeNumber() {
    int value1 = 12;
    int value2 = -15;
    assertEquals(value1 + value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicAdditionOfOneNegativeAndOnePositiveNumber() {
    int value1 = -12;
    int value2 = 15;
    assertEquals(value1 + value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicAdditionNegativeNumbers() {
    int value1 = -12;
    int value2 = -15;
    assertEquals(value1 + value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testAdditionOfLargePositiveValuesTruncates() {
    int value = Integer.MAX_VALUE;
    assertEquals(value, OPERATOR.apply(value, value));
  }

  @Test
  public void testAdditionOfLargeNegativeValuesTruncates() {
    int value = Integer.MIN_VALUE;
    assertEquals(value, OPERATOR.apply(value, value));
  }

}