import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StackOverflowExceptionTest {

  @Test
  public void testConstructor() {
    String expectedMessage = "Stack overflow.";
    assertEquals(expectedMessage, new StackOverflowException().getMessage());
  }

}