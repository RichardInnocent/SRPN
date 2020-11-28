import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DivisionOperatorTest {

  private static final Operator OPERATOR = new DivisionOperator();

  @Test
  public void testBasicDivisionOfPositiveNumbers() {
    double value1 = 120d;
    double value2 = 15d;
    assertEquals(value1 / value2, OPERATOR.calculate(value1, value2), 1e-10);
  }

  @Test
  public void testBasicDivisionOfOnePositiveAndOneNegativeNumber() {
    double value1 = 120d;
    double value2 = -15d;
    assertEquals(value1 / value2, OPERATOR.calculate(value1, value2), 1e-10);
  }

  @Test
  public void testBasicDivisionOfOneNegativeAndOnePositiveNumber() {
    double value1 = -120d;
    double value2 = 15d;
    assertEquals(value1 / value2, OPERATOR.calculate(value1, value2), 1e-10);
  }

  @Test
  public void testBasicDivisionOfNegativeNumbers() {
    double value1 = -120d;
    double value2 = -15d;
    assertEquals(value1 / value2, OPERATOR.calculate(value1, value2), 1e-10);
  }

  @Test
  public void testDivisionOfLargePositiveValues() {
    assertEquals(Integer.MAX_VALUE, OPERATOR.calculate(Integer.MAX_VALUE, 1), 1e-10);
  }

  @Test
  public void testDivisionOfLargeNegativeValues() {
    assertEquals(Integer.MIN_VALUE, OPERATOR.calculate(Integer.MIN_VALUE, 1), 1e-10);
  }

  @Test
  public void testDivisionOfByVerySmallNumberThatIsNotZero() {
    double numerator = 5;
    double denominator = (double) (Integer.MIN_VALUE+1) / Integer.MIN_VALUE;
    assertEquals(numerator / denominator, OPERATOR.calculate(numerator, denominator), 1e-8);
  }

  @Test(expected = DivideByZeroException.class)
  public void testDivisionByVerySmallNumberThatIsClassifiedAsZero() {
    OPERATOR.calculate(5, 1e-11d);
  }

  @Test
  public void testReadableName() {
    assertEquals("/", OPERATOR.getReadableName());
  }

  @Test
  public void testPrecedence() {
    assertEquals(OperatorPrecedence.DIVISION, OPERATOR.getPrecedence());
  }

}