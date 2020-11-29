/**
 * Token representing a raise to power operation.
 */
public class RaiseToPowerToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    // The RaiseToPowerOperator will handle the calculations
    return new RaiseToPowerOperator();
  }
}
