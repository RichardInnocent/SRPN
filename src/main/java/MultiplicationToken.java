/**
 * The token representing a multiplication.
 */
public class MultiplicationToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    // The MultiplicationOperator will handle the application of this token
    return new MultiplicationOperator();
  }
}
