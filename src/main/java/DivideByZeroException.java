/**
 * Exception caused when attempting to divide by 0.
 */
public class DivideByZeroException extends CalculatorException {

  /**
   * Creates a new divide by 0 exception.
   */
  public DivideByZeroException() {
    super("Divide by 0.");
  }

}
