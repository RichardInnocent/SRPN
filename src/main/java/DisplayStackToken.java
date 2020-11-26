/**
 * Token for when the user requests to view the contents of the stack.
 */
public class DisplayStackToken extends AbstractToken {

  @Override
  public void apply(SizeRestrictedStack<Double> operandStack) throws CalculatorException {
    if (operandStack.isEmpty()) {
      /* If the stack is empty, the original calculator displays the lowest value integer for some
       * reason. */
      System.out.println(Integer.MIN_VALUE);
    } else {
      // Print out each element of the stack from first in to last in
      operandStack.stream().mapToInt(Double::intValue).forEach(System.out::println);
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
