import static org.junit.Assert.*;

import org.junit.Test;

public class DivisionTokenTest {

  @Test
  public void testToken() {
    assertTrue(new DivisionToken().getOperator() instanceof DivisionOperator);
  }

}