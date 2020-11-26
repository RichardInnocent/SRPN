public interface Token {

  String getReadableValue();
  void apply(SizeRestrictedStack<Double> operandStack) throws CalculatorException;

}
