package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.operator;

import uk.ac.bath.cm50273.calc.srpn.CalculatorException;
import uk.ac.bath.cm50273.calc.srpn.compiler.operators.Operator;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.AbstractToken;
import uk.ac.bath.cm50273.calc.srpn.execution.SizeRestrictedStack;
import uk.ac.bath.cm50273.calc.srpn.execution.StackUnderflowException;

public abstract class OperatorToken extends AbstractToken {

  @Override
  public void apply(SizeRestrictedStack<Integer> operandStack) throws CalculatorException {
    int stackSize = operandStack.size();
    if (stackSize >= 2) {
      int operand1 = operandStack.get(stackSize-1);
      int operand2 = operandStack.get(stackSize-2);
      int result = getOperator().apply(operand2, operand1);

      // Only pop operands off of the stack if the apply method is successful
      operandStack.pop();
      operandStack.pop();
      operandStack.push(result);
    } else {
      throw new StackUnderflowException();
    }
  }

  public abstract Operator getOperator();

  public String getReadableValue() {
    return getOperator().getReadableName();
  }

  @Override
  public int hashCode() {
    return getOperator().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof OperatorToken)) {
      return false;
    }

    return getOperator().equals(((OperatorToken) o).getOperator());
  }

}
