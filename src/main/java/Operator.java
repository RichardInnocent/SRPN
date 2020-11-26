import java.math.BigDecimal;

public abstract class Operator implements Comparable<Operator> {

  private static final double MAX_VALUE = Integer.MAX_VALUE;
  private static final double MIN_VALUE = Integer.MIN_VALUE;
  private static final BigDecimal BIG_DECIMAL_MAX_VALUE = new BigDecimal(MAX_VALUE);
  private static final BigDecimal BIG_DECIMAL_MIN_VALUE = new BigDecimal(MIN_VALUE);

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

  protected final boolean isAboveBound(BigDecimal value) {
    return value.compareTo(BIG_DECIMAL_MAX_VALUE) > 0;
  }

  protected final boolean isBelowBound(BigDecimal value) {
    return value.compareTo(BIG_DECIMAL_MIN_VALUE) < 0;
  }

  protected final double truncateToBounds(BigDecimal value) {
    if (isAboveBound(value)) {
      return MAX_VALUE;
    }
    if (isBelowBound(value)) {
      return MIN_VALUE;
    }
    return value.intValue();
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

  public abstract int getPrecedence();

  @Override
  public int compareTo(Operator o) {
    // Precedence is in reverse - higher precedence means that it should appear first
    return Integer.compare(o.getPrecedence(), getPrecedence());
  }
}
