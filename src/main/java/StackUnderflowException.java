/**
 * An exception thrown by the legacy SRPN whenever attempting to pop operands off of an empty
 * stack.
 */
public class StackUnderflowException extends CalculatorException {

  /**
   * Creates a new exception imitating the exception thrown by the legacy SRPN whenever attempting
   * to pop operands off of an empty stack.
   */
  public StackUnderflowException() {
    super("Stack underflow.");
  }

}
