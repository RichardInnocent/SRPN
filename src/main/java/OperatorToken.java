public abstract class OperatorToken extends AbstractToken implements Comparable<OperatorToken> {

  public abstract Operator getOperator();

  @Override
  public void apply(SizeRestrictedStack<Double> operandStack) throws CalculatorException {
    if (operandStack.size() < 2) {
      throw new StackUnderflowException();
    }
    double operand1 = operandStack.get(operandStack.size() - 2);
    double operand2 = operandStack.get(operandStack.size() - 1);

    double result = getOperator().calculate(operand1, operand2);

    // If this works, we can pop both of the operands off the stack and then push on the result
    operandStack.pop();
    operandStack.pop();
    operandStack.push(result);
  }

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

  @Override
  public int compareTo(OperatorToken o) {
    return getOperator().compareTo(o.getOperator());
  }
}
