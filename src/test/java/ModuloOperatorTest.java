import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ModuloOperatorTest {

  private static final double DELTA = 1e-8;
  private static final Operator OPERATOR = new ModuloOperator();

  @Test
  public void testBasicModulusOfPositiveNumbers() {
    int value1 = 121;
    int value2 = 15;
    assertEquals(value1 % value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testBasicModulusOfOnePositiveAndOneNegativeNumber() {
    int value1 = 121;
    int value2 = -15;
    assertEquals(value1 % value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testBasicModulusOfOneNegativeAndOnePositiveNumber() {
    int value1 = -121;
    int value2 = 15;
    assertEquals(value1 % value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testBasicModulusOfNegativeNumbers() {
    int value1 = -121;
    int value2 = -15;
    assertEquals(value1 % value2, OPERATOR.calculate(value1, value2), DELTA);
  }

  @Test
  public void testModulusOfLargePositiveValues() {
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(Integer.MAX_VALUE, Integer.MIN_VALUE), DELTA);
  }

}