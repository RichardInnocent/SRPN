import static org.junit.Assert.*;

import org.junit.Test;

public class MultiplicationTokenTest {

  @Test
  public void testGetOperator() {
    assertTrue(new MultiplicationToken().getOperator() instanceof MultiplicationOperator);
  }

}