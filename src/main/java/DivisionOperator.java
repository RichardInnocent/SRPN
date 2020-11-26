import java.math.BigDecimal;
import java.math.RoundingMode;

public class DivisionOperator extends Operator {

  @Override
  public double apply(double operand1, double operand2) {
    if (Math.abs(operand2) < 1e-10d) {
      throw new DivideByZeroException();
    }
    return operand2 > -1d && operand2 < 1d ?
        performBigDecimalDivision(operand1, operand2) :
        performStandardDivision(operand1, operand2);
  }

  private double performStandardDivision(double operand1, double operand2) {
    return truncateToBounds(operand1 / operand2);
  }

  private double performBigDecimalDivision(double operand1, double operand2) {
    BigDecimal result =
        new BigDecimal(operand1).divide(new BigDecimal(operand2), RoundingMode.DOWN);
    return truncateToBounds(result);
  }

  @Override
  public String getReadableName() {
    return "/";
  }

}
