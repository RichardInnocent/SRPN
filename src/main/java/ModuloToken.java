public class ModuloToken extends OperatorToken {

  @Override
  public Operator getOperator() {
    return new ModuloOperator();
  }

}