package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc;

import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.AbstractToken;
import uk.ac.bath.cm50273.calc.srpn.execution.SizeRestrictedStack;
import uk.ac.bath.cm50273.calc.srpn.execution.StackOverflowException;
import uk.ac.bath.cm50273.calc.srpn.execution.StackUnderflowException;

/**
 * Token for the equals sign (i.e. peeking on the stack).
 */
public class EqualsToken extends AbstractToken {

  @Override
  public void apply(SizeRestrictedStack<Integer> operandStack)
      throws StackUnderflowException, StackOverflowException {
    if (operandStack.isEmpty()) {
      System.out.println("Stack empty.");
    } else {
      System.out.println(operandStack.peek());
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
