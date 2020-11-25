public interface Token {

  void apply(SizeRestrictedStack<Double> operandStack) throws CalculatorException;
  String getReadableValue();

}
