import static org.junit.Assert.*;

import org.junit.Test;

public class DummyFloatingPointExceptionTest {

  @Test
  public void testConstructorMessage() {
    assertEquals(
        "Floating point exception (core dumped)",
        new DummyFloatingPointException().getMessage()
    );
  }

}