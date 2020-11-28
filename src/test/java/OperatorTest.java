import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class OperatorTest {

  private static final double DELTA = 1e-8;

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
    assertEquals(Integer.MIN_VALUE, operator.truncateToBounds(Long.MIN_VALUE), DELTA);
    assertEquals(Integer.MIN_VALUE, operator.truncateToBounds(Integer.MIN_VALUE - 1L), DELTA);
    assertEquals(Integer.MIN_VALUE, operator.truncateToBounds(Integer.MIN_VALUE), DELTA);
    assertEquals(-50, operator.truncateToBounds(-50L), DELTA);
    assertEquals(0, operator.truncateToBounds(0L), DELTA);
    assertEquals(50, operator.truncateToBounds(50L), DELTA);
    assertEquals(Integer.MAX_VALUE, operator.truncateToBounds(Integer.MAX_VALUE), DELTA);
    assertEquals(Integer.MAX_VALUE, operator.truncateToBounds(Integer.MAX_VALUE + 1L), DELTA);
    assertEquals(Integer.MAX_VALUE, operator.truncateToBounds(Long.MAX_VALUE), DELTA);
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

  @Test
  public void testCompareTo() {
    Operator highestPrecedence = createOperatorWithPrecedence(2);
    Operator middlePrecedence = createOperatorWithPrecedence(1);
    Operator lowestPrecedence = createOperatorWithPrecedence(0);

    assertTrue(highestPrecedence.compareTo(middlePrecedence) < 0);
    assertTrue(highestPrecedence.compareTo(lowestPrecedence) < 0);

    assertTrue(middlePrecedence.compareTo(highestPrecedence) > 0);
    assertTrue(middlePrecedence.compareTo(lowestPrecedence) < 0);

    assertTrue(lowestPrecedence.compareTo(highestPrecedence) > 0);
    assertTrue(lowestPrecedence.compareTo(middlePrecedence) > 0);

    List<Operator> operators = Arrays.asList(lowestPrecedence, middlePrecedence, highestPrecedence);
    Collections.sort(operators);
    assertSame(highestPrecedence, operators.get(0));
    assertSame(middlePrecedence, operators.get(1));
    assertSame(lowestPrecedence, operators.get(2));
  }

  private static Operator createOperatorWithReadableName(String readableName) {
    return new Operator() {
      @Override
      public double calculate(double operand1, double operand2) {
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

  private static Operator createOperatorWithPrecedence(int precedence) {
    return new Operator() {
      @Override
      public double calculate(double operand1, double operand2) {
        return 0;
      }

      @Override
      public String getReadableName() {
        return "Precedence " + precedence;
      }

      @Override
      public int getPrecedence() {
        return precedence;
      }
    };
  }

}