import java.util.Objects;

/**
 * Token representing the generation of a random number.
 */
public class RandomNumberToken extends AbstractToken {

  private final RandomNumberGenerator randomNumberGenerator;

  /**
   * Creates a new random number token. This will use the standard SRPN random number generation
   * process to create operands.
   */
  public RandomNumberToken() {
    this(SrpnRandomNumberGenerator.getInstance());
  }

  /**
   * Creates a random number token, allowing the caller to specify the random number generator. This
   * allows the class to be tested more easily.
   * @param randomNumberGenerator The random number generated used when calling
   * {@link Token#apply(SizeRestrictedStack)}.
   */
  RandomNumberToken(RandomNumberGenerator randomNumberGenerator) {
    this.randomNumberGenerator =
        Objects.requireNonNull(randomNumberGenerator, "Random number generator is null");
  }

  @Override
  public void apply(SizeRestrictedStack<Double> operandStack) throws CalculatorException {
    /* Only generate the random number if the stack isn't full so we don't progress the sequence.
     * If the stack is full, throw an exception. Otherwise, add a random number to the stack. */
    operandStack.doIfNotFullOrThrowException(
        stack -> operandStack.push((double) randomNumberGenerator.nextInt())
    );
  }

  @Override
  public String getReadableValue() {
    return "random";
  }
}
