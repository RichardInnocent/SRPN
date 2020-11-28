import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AdditionOperatorTest {

  private static final double DELTA = 1e-8;
  private static final Operator OPERATOR = new AdditionOperator();

  @Test
  public void testBasicAdditionOfPositiveNumbers() {
    int value1 = 12;
    int value2 = 15;
    assertEquals(value1 + value2, OPERATOR.apply(value1, value2), DELTA);
  }

  @Test
  public void testBasicAdditionOfOnePositiveAndOneNegativeNumber() {
    int value1 = 12;
    int value2 = -15;
    assertEquals(value1 + value2, OPERATOR.apply(value1, value2), DELTA);
  }

  @Test
  public void testBasicAdditionOfOneNegativeAndOnePositiveNumber() {
    int value1 = -12;
    int value2 = 15;
    assertEquals(value1 + value2, OPERATOR.apply(value1, value2), DELTA);
  }

  @Test
  public void testBasicAdditionNegativeNumbers() {
    int value1 = -12;
    int value2 = -15;
    assertEquals(value1 + value2, OPERATOR.apply(value1, value2), DELTA);
  }

  @Test
  public void testAdditionOfLargePositiveValuesTruncates() {
    int value = Integer.MAX_VALUE;
    assertEquals(value, OPERATOR.apply(value, value), DELTA);
  }

  @Test
  public void testAdditionOfLargeNegativeValuesTruncates() {
    int value = Integer.MIN_VALUE;
    assertEquals(value, OPERATOR.apply(value, value), DELTA);
  }

  @Test
  public void testReadableName() {
    assertEquals("+", OPERATOR.getReadableName());
  }

  @Test
  public void testPrecedence() {
    assertEquals(OperatorPrecedence.ADDITION, OPERATOR.getPrecedence());
  }

}