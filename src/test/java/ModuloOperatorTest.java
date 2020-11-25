import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ModuloOperatorTest {

  private static final Operator OPERATOR = new ModuloOperator();

  @Test
  public void testBasicModulusOfPositiveNumbers() {
    int value1 = 121;
    int value2 = 15;
    assertEquals(value1 % value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicModulusOfOnePositiveAndOneNegativeNumber() {
    int value1 = 121;
    int value2 = -15;
    assertEquals(value1 % value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicModulusOfOneNegativeAndOnePositiveNumber() {
    int value1 = -121;
    int value2 = 15;
    assertEquals(value1 % value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testBasicModulusOfNegativeNumbers() {
    int value1 = -121;
    int value2 = -15;
    assertEquals(value1 % value2, OPERATOR.apply(value1, value2));
  }

  @Test
  public void testModulusOfLargePositiveValues() {
    assertEquals(Integer.MAX_VALUE, OPERATOR.apply(Integer.MAX_VALUE, Integer.MIN_VALUE));
  }

}