import static org.junit.Assert.*;

import org.junit.Test;

public class StackUnderflowExceptionTest {

  @Test
  public void testMessage() {
    assertEquals("Stack underflow.", new StackUnderflowException().getMessage());
  }

}