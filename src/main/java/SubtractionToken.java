/**
 * Token to represent a subtraction operation.
 */
public class SubtractionToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    // The subtraction operator will handle the application of this token
    return new SubtractionOperator();
  }
}
