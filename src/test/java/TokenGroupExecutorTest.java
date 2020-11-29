import static org.junit.Assert.*;

import org.junit.Test;

public class TokenGroupExecutorTest {

  @Test(expected = NullPointerException.class)
  public void testOperandStackCannotBeNull() {
    new TokenGroupExecutor(null);
  }

  @Test
  public void testGroupOfOperandsTokensAreAllAddedToStack() {
    SizeRestrictedStack<Double> operandStack = new SizeRestrictedStack<>(3);
    TokenGroupExecutor executor = new TokenGroupExecutor(operandStack);

    int operand1 = 10;
    int operand2 = 20;
    int operand3 = 30;
    TokenGroup tokenGroup = new TokenGroup();
    tokenGroup.addAndTidy(OperandToken.forValue(operand1));
    tokenGroup.addAndTidy(OperandToken.forValue(operand2));
    tokenGroup.addAndTidy(OperandToken.forValue(operand3));

    executor.execute(tokenGroup);
    assertEquals(3, operandStack.size());
    assertEquals(operand1, operandStack.get(0).intValue());
    assertEquals(operand2, operandStack.get(1).intValue());
    assertEquals(operand3, operandStack.get(2).intValue());
  }

  @Test
  public void testExecutionOrderOfGroupWithOperators() {
    SizeRestrictedStack<Double> operandStack = new SizeRestrictedStack<>(5);
    TokenGroupExecutor executor = new TokenGroupExecutor(operandStack);

    int operand1 = 45;
    int operand2 = -3;
    int operand3 = 67;
    int operand4 = 4;
    int operand5 = 10;

    TokenGroup tokenGroup = new TokenGroup();
    tokenGroup.addAndTidy(OperandToken.forValue(operand1));
    tokenGroup.addAndTidy(new MultiplicationToken());
    tokenGroup.addAndTidy(OperandToken.forValue(operand2));
    tokenGroup.addAndTidy(new DivisionToken());
    tokenGroup.addAndTidy(OperandToken.forValue(operand3));
    tokenGroup.addAndTidy(new SubtractionToken());
    tokenGroup.addAndTidy(OperandToken.forValue(operand4));
    tokenGroup.addAndTidy(new MultiplicationToken());
    tokenGroup.addAndTidy(OperandToken.forValue(operand5));

    executor.execute(tokenGroup);
    assertEquals(1, operandStack.size());
    assertEquals(-42.0149, operandStack.get(0), 1e-4);
  }

}