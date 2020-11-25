public interface Token {

  void apply(SizeRestrictedStack<Integer> operandStack) throws CalculatorException;
  String getReadableValue();

}
