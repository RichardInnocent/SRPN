public class RandomNumberToken extends AbstractToken {

  private static final SrpnRandomNumberGenerator RANDOM_NUMBER_GENERATOR =
      new SrpnRandomNumberGenerator();

  @Override
  public void apply(SizeRestrictedStack<Integer> operandStack)
      throws StackUnderflowException, StackOverflowException {
    operandStack.doIfNotFull(stack -> {
      operandStack.push(RANDOM_NUMBER_GENERATOR.nextInt());
    });
  }

  @Override
  public String getReadableValue() {
    return "random";
  }
}
