import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.function.BiFunction;
import org.junit.Test;

public class OperatorTokenTest {

  private final OperatorToken token = createOperatorTokenFromOperator(Double::sum);

  @Test(expected = StackUnderflowException.class)
  public void testApplyThrowsUnderflowExceptionIfStackIsEmpty() {
    SizeRestrictedStack<Double> operandStack = new SizeRestrictedStack<>(1);
    token.apply(operandStack);
  }

  @Test(expected = StackUnderflowException.class)
  public void testApplyThrowsUnderflowExceptionIfStackOnlyHasOneElement() {
    SizeRestrictedStack<Double> operandStack = new SizeRestrictedStack<>(1);
    operandStack.push(10.2);
    token.apply(operandStack);
  }

  @Test
  public void testSuccessfulApplication() {
    SizeRestrictedStack<Double> operandStack = new SizeRestrictedStack<>(2);
    double operand1 = 10.2;
    double operand2 = 13.8;
    operandStack.push(operand1);
    operandStack.push(operand2);
    token.apply(operandStack);
    assertEquals(1, operandStack.size());
    assertEquals(operand1 + operand2, operandStack.peek(), 1e-8);
  }

  @Test
  public void testUnsuccessfulApplication() {
    SizeRestrictedStack<Double> operandStack = new SizeRestrictedStack<>(2);
    double operand1 = 10.2;
    double operand2 = 13.8;
    operandStack.push(operand1);
    operandStack.push(operand2);

    Token token =
        createOperatorTokenFromOperator(
            (op1, op2) -> {throw new CalculatorException("Test exception"){};}
        );

    try {
      token.apply(operandStack);
      fail("No exception thrown");
    } catch (CalculatorException e) {
      assertEquals(2, operandStack.size());
      assertEquals(operand1, operandStack.get(0), 1e-8);
      assertEquals(operand2, operandStack.get(1), 1e-8);
    }
  }

  @Test
  public void testHashCode() {
    Operator operator = mock(Operator.class);
    assertEquals(operator.hashCode(), createOperatorTokenFromOperator(operator).hashCode());
  }

  @Test
  public void testEquals() {
    Operator operator1 = mock(Operator.class);
    Operator operator2 = mock(Operator.class);

    OperatorToken token1a = createOperatorTokenFromOperator(operator1);
    OperatorToken token1b = createOperatorTokenFromOperator(operator1);
    OperatorToken token2 = createOperatorTokenFromOperator(operator2);

    assertTrue(token1a.equals(token1b));
    assertTrue(token1b.equals(token1a)); // ensure symmetric
    assertFalse(token1a.equals(token2));
    assertFalse(token2.equals(token1a)); // ensure symmetric
  }

  @Test
  public void testCompareTo() {
    OperatorToken highestPrecedence = createOperatorTokenWithPrecedence(2);
    OperatorToken mediumPrecedence = createOperatorTokenWithPrecedence(1);
    OperatorToken lowestPrecedence = createOperatorTokenWithPrecedence(0);

    assertTrue(highestPrecedence.compareTo(mediumPrecedence) < 0);
    assertTrue(mediumPrecedence.compareTo(highestPrecedence) > 0);

    assertTrue(mediumPrecedence.compareTo(lowestPrecedence) < 0);
    assertTrue(lowestPrecedence.compareTo(mediumPrecedence) > 0);
  }

  private static OperatorToken createOperatorTokenFromOperator(
      BiFunction<Double, Double, Double> calculation) {
    return createOperatorTokenFromOperator(createOperator(calculation));
  }

  private static OperatorToken createOperatorTokenFromOperator(Operator operator) {
    return new OperatorToken() {
      @Override
      public Operator getOperator() {
        return operator;
      }
    };
  }

  private static Operator createOperator(BiFunction<Double, Double, Double> calculation) {
    return new Operator() {
      @Override
      public double calculate(double operand1, double operand2) {
        return calculation.apply(operand1, operand2);
      }

      @Override
      public String getReadableName() {
        return null;
      }

      @Override
      public int getPrecedence() {
        return 0;
      }
    };
  }

  private static OperatorToken createOperatorTokenWithPrecedence(int precedence) {
    return new OperatorToken() {

      private final Operator operator = createOperatorWithPrecedence(precedence);

      @Override
      public Operator getOperator() {
        return operator;
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