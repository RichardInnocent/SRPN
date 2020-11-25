/**
 * The token representing an addition.
 */
public class AdditionToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    // The AdditionOperator will handle the application of this token.
    return new AdditionOperator();
  }

}
