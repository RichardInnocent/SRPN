public abstract class Operator {

  private static final double MAX_VALUE = Integer.MAX_VALUE;
  private static final double MIN_VALUE = Integer.MIN_VALUE;

  public abstract double apply(double operand1, double operand2);
  public abstract String getReadableName();

  protected final boolean isAboveBound(double value) {
    return value > MAX_VALUE;
  }

  protected final boolean isBelowBound(double value) {
    return value < MIN_VALUE;
  }

  protected final double truncateToBounds(double value) {
    if (isAboveBound(value)) {
      return MAX_VALUE;
    }
    if (isBelowBound(value)) {
      return MIN_VALUE;
    }
    return value;
  }

  @Override
  public int hashCode() {
    return getReadableName().hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    return o instanceof Operator &&
        getReadableName().equals(((Operator) o).getReadableName());
  }

}
