import static org.junit.Assert.*;

import org.junit.Test;

public class ExecutionStateTest {

  @Test
  public void testSucceededIsSuccessful() {
    assertTrue(ExecutionState.SUCCEEDED.succeeded());
  }

  @Test
  public void testFailedIsNotSuccessful() {
    assertFalse(ExecutionState.FAILED.succeeded());
  }

}