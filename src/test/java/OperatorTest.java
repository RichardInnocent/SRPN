import static org.junit.Assert.*;

import org.junit.Test;

public class OperatorTest {

  @Test
  public void testIsAboveBound() {
    Operator operator = createOperatorWithReadableName("Operator");
    assertFalse(operator.isAboveBound(Long.MIN_VALUE));
    assertFalse(operator.isAboveBound(0L));
    assertFalse(operator.isAboveBound(Integer.MAX_VALUE));
    assertTrue(operator.isAboveBound(Integer.MAX_VALUE + 1L));
    assertTrue(operator.isAboveBound(Long.MAX_VALUE));
  }

  @Test
  public void testIsBelowBound() {
    Operator operator = createOperatorWithReadableName("Operator");
    assertTrue(operator.isBelowBound(Long.MIN_VALUE));
    assertTrue(operator.isBelowBound(Integer.MIN_VALUE - 1L));
    assertFalse(operator.isBelowBound(0L));
    assertFalse(operator.isBelowBound(Long.MAX_VALUE));
  }

  @Test
  public void testTruncateToBounds() {
    Operator operator = createOperatorWithReadableName("Operator");
    assertEquals(Integer.MIN_VALUE, operator.truncateToBounds(Long.MIN_VALUE));
    assertEquals(Integer.MIN_VALUE, operator.truncateToBounds(Integer.MIN_VALUE - 1L));
    assertEquals(Integer.MIN_VALUE, operator.truncateToBounds(Integer.MIN_VALUE));
    assertEquals(-50, operator.truncateToBounds(-50L));
    assertEquals(0, operator.truncateToBounds(0L));
    assertEquals(50, operator.truncateToBounds(50L));
    assertEquals(Integer.MAX_VALUE, operator.truncateToBounds(Integer.MAX_VALUE));
    assertEquals(Integer.MAX_VALUE, operator.truncateToBounds(Integer.MAX_VALUE + 1L));
    assertEquals(Integer.MAX_VALUE, operator.truncateToBounds(Long.MAX_VALUE));
  }

  @Test
  public void testHashCode() {
    String readableValue = "Operator";
    Operator operator = createOperatorWithReadableName(readableValue);
    assertEquals(readableValue.hashCode(), operator.hashCode());
  }

  @Test
  public void testEquals() {
    String readableValue1 = "readable value 1";
    String readableValue2 = "readable value 2";

    Operator operator1a = createOperatorWithReadableName(readableValue1);
    Operator operator1b = createOperatorWithReadableName(readableValue1);
    Operator operator2 = createOperatorWithReadableName(readableValue2);

    assertTrue(operator1a.equals(operator1b));
    assertTrue(operator1b.equals(operator1a));
    assertFalse(operator2.equals(operator1a));
    assertFalse(operator1a.equals(operator2));
  }

  private static Operator createOperatorWithReadableName(String readableName) {
    return new Operator() {
      @Override
      public double apply(double operand1, double operand2) {
        return 0;
      }

      @Override
      public int getPrecedence() {
        return 0;
      }

      @Override
      public String getReadableName() {
        return readableName;
      }
    };
  }

}