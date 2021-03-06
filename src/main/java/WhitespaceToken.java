/**
 * A token that contains only whitespace.
 */
public class WhitespaceToken extends AbstractToken {

  @Override
  public String getReadableValue() {
    return "whitespace";
  }

  @Override
  public void apply(SizeRestrictedStack<Double> operandStack) throws CalculatorException {
    // Do nothing - whitespace has no effect on the stack, it only affects the grouping of tokens
  }

  @Override
  public int hashCode() {
    return getReadableValue().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof WhitespaceToken;
  }
}
