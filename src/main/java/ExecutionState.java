import java.util.function.Predicate;

public enum ExecutionState {

  SUCCEEDED(true),
  FAILED(false);

  private final boolean succeeded;

  ExecutionState(boolean succeeded) {
    this.succeeded = succeeded;
  }

  public boolean succeeded() {
    return succeeded;
  }

}
