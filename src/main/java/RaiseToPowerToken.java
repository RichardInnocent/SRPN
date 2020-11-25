public class RaiseToPowerToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    return new RaiseToPowerOperator();
  }
}
