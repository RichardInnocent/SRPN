public class SubtractionToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    return new SubtractionOperator();
  }
}
