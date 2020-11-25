public class RandomNumberToken extends AbstractToken {

  private static final SrpnRandomNumberGenerator RANDOM_NUMBER_GENERATOR =
      new SrpnRandomNumberGenerator();

  @Override
  public void apply(SizeRestrictedStack<Double> operandStack)
      throws StackUnderflowException, StackOverflowException {
    operandStack.doIfNotFull(stack -> {
      operandStack.push((double) RANDOM_NUMBER_GENERATOR.nextInt());
    });
  }

  @Override
  public String getReadableValue() {
    return "random";
  }
}
