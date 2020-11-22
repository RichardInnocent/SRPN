package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.misc;

import uk.ac.bath.cm50273.calc.srpn.CalculatorException;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.AbstractToken;
import uk.ac.bath.cm50273.calc.srpn.execution.SizeRestrictedStack;

/**
 * Token for when the user requests to view the contents of the stack.
 */
public class DisplayStackToken extends AbstractToken {

  @Override
  public void apply(SizeRestrictedStack<Integer> operandStack) throws CalculatorException {
    if (operandStack.isEmpty()) {
      System.out.println(Integer.MIN_VALUE);
    } else {
      operandStack.forEach(System.out::println);
    }
  }

  @Override
  public String getReadableValue() {
    return "display";
  }

  @Override
  public int hashCode() {
    return getReadableValue().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof DisplayStackToken;
  }
}
