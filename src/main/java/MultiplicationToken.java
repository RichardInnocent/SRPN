public class MultiplicationToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    return new MultiplicationOperator();
  }
}
