/**
 * A calculator exception represents a failed operation, but one that was handled successfully by
 * the original SRPN.
 */
public abstract class CalculatorException extends RuntimeException {

  /**
   * Creates a new exception indicating a failed exception. This exception type should be caught by
   * the calculator and handled appropriately.
   * @param message The exception message.
   */
  protected CalculatorException(String message) {
    super(message);
  }

}
