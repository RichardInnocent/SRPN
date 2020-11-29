/**
 * Exception thrown by the legacy SRPN whenever an operand is attempted to be pushed onto a full
 * stack.
 */
public class StackOverflowException extends CalculatorException {

  /**
   * Creates a new exception representing the exception thrown by the legacy SRPN whenever an
   * operand is attempted to be pushed onto a full stack.
   */
  public StackOverflowException() {
    super("Stack overflow.");
  }

}
