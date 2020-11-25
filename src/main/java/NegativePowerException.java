/**
 * An exception thrown by the legacy SRPN when a number is attempted to be raised to a negative
 * power.
 */
public class NegativePowerException extends CalculatorException {

  /**
   * Creates a new exception to indicate that a number was attempted to be raised to a negative
   * power. This was disallowed in the legacy SRPN.
   */
  public NegativePowerException() {
    super("Negative power.");
  }

}
