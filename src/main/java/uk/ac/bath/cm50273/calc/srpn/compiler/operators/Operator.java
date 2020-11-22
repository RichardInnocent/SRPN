package uk.ac.bath.cm50273.calc.srpn.compiler.operators;

public abstract class Operator {

  private static final int MAX_VALUE = Integer.MAX_VALUE;
  private static final int MIN_VALUE = Integer.MIN_VALUE;

  public abstract int apply(int operand1, int operand2);
  public abstract String getReadableName();

  protected final boolean isAboveBound(long value) {
    return value > MAX_VALUE;
  }

  protected final boolean isBelowBound(long value) {
    return value < MIN_VALUE;
  }

  protected final int truncateToBounds(long value) {
    if (isAboveBound(value)) {
      return MAX_VALUE;
    }
    if (isBelowBound(value)) {
      return MIN_VALUE;
    }
    return (int) value;
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
