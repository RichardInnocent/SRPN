import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DivisionOperatorTest {

  private static final Operator OPERATOR = new DivisionOperator();

  @Test
  public void testBasicDivisionOfPositiveNumbers() {
    int value1 = 120;
    int value2 = 15;
    assertEquals(value1 / value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicDivisionOfOnePositiveAndOneNegativeNumber() {
    int value1 = 120;
    int value2 = -15;
    assertEquals(value1 / value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicDivisionOfOneNegativeAndOnePositiveNumber() {
    int value1 = -120;
    int value2 = 15;
    assertEquals(value1 / value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicDivisionOfNegativeNumbers() {
    int value1 = -120;
    int value2 = -15;
    assertEquals(value1 / value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testDivisionOfLargePositiveValues() {
    assertEquals(Integer.MAX_VALUE, OPERATOR.apply(Integer.MAX_VALUE, 1));
  }

  @Test
  public void testDivisionOfLargeNegativeValues() {
    assertEquals(Integer.MIN_VALUE, OPERATOR.apply(Integer.MIN_VALUE, 1));
  }

}