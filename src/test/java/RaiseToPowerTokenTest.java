import static org.junit.Assert.*;

import org.junit.Test;

public class RaiseToPowerTokenTest {

  @Test
  public void testOperator() {
    assertTrue(new RaiseToPowerToken().getOperator() instanceof RaiseToPowerOperator);
  }

}