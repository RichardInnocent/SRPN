/**
 * Responsible for generating random numbers.
 */
@FunctionalInterface
public interface RandomNumberGenerator {

  /**
   * Gets the next random number.
   * @return The next random number.
   */
  int nextInt();

}
