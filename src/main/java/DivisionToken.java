/**
 * The token representing an attempted division operation.
 */
public class DivisionToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    // The division operator will handle division operations
    return new DivisionOperator();
  }

}
