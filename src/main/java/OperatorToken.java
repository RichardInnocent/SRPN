public abstract class OperatorToken extends AbstractToken {

  public abstract Operator getOperator();

  public String getReadableValue() {
    return getOperator().getReadableName();
  }

  @Override
  public int hashCode() {
    return getOperator().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof OperatorToken)) {
      return false;
    }

    return getOperator().equals(((OperatorToken) o).getOperator());
  }

}
