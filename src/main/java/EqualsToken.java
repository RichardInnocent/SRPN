/**
 * Token for the equals sign. This token has the effect of printing the most recent item on the
 * stack to the terminal, without removing it.
 */
public class EqualsToken extends AbstractToken {

  /**
   * The equals token prints the most recently added operand on the stack without removing it.
   * @param operandStack The operand stack.
   */
  @Override
  public void apply(SizeRestrictedStack<Double> operandStack) {
    if (operandStack.isEmpty()) {
      // Stack is empty so print this to the terminal
      System.out.println("Stack empty.");
    } else {
      // Show the most recently added number on the stack
      System.out.println(operandStack.peek().intValue());
    }
  }

  @Override
  public String getReadableValue() {
    return "equals";
  }

  @Override
  public int hashCode() {
    return getReadableValue().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof EqualsToken;
  }

}
