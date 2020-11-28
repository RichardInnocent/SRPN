import static org.junit.Assert.*;

import org.junit.Test;

public class AdditionTokenTest {

  @Test
  public void testOperator() {
    assertTrue(new AdditionToken().getOperator() instanceof AdditionOperator);
  }

}