/**
 * Matches the random number generator used by the legacy SRPN. While this loosely follows the
 * GCC implementation of an unseeded {@code rand()} call in C, it does repeat the first 22 values
 * for numbers 23 - 44 (inclusive), before continuing the sequence.
 */
public class SrpnRandomNumberGenerator implements RandomNumberGenerator {

  /**
   * Singleton instance.
   */
  private static final SrpnRandomNumberGenerator INSTANCE = new SrpnRandomNumberGenerator();

  private int count = 0;
  private GccRandomNumberGenerator randomNumberGenerator = new GccRandomNumberGenerator();

  /**
   * Force use of the {@link #getInstance()} singleton method.
   */
  private SrpnRandomNumberGenerator() {}

  /**
   * Gets the instance of the random number generator.
   * @return The instance of the random number generator.
   */
  public static SrpnRandomNumberGenerator getInstance() {
    return INSTANCE;
  }

  @Override
  public int nextInt() {
    if (count == 22) {
      // Start from the beginning of the sequence again
      randomNumberGenerator = new GccRandomNumberGenerator();
    }
    count++;
    return randomNumberGenerator.nextInt();
  }

}
