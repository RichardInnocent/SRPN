/**
 * Denotes the state of an execution. While this mirrors a simple {@code boolean} state, it
 * can improve the clarity of the calling code. Consider the readability of the two following
 * examples:
 * <pre><code>
  // Returning a boolean
  if (performOperation()) { ... }}

  // Returning an ExecutionState
  if (performOperation().succeeded) { ... }</code></pre>
 */
public enum ExecutionState {

  /**
   * The execution of the operation was successful.
   */
  SUCCEEDED(true),

  /**
   * The execution of the operation was not successful.
   */
  FAILED(false);

  private final boolean succeeded;

  ExecutionState(boolean succeeded) {
    this.succeeded = succeeded;
  }

  /**
   * Conveys whether the execution of the operation was successful.
   * @return {@code true} if the execution of the operation was successful.
   */
  public boolean succeeded() {
    return succeeded;
  }

}
