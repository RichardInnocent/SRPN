import static org.junit.Assert.*;

import org.junit.Test;

public class SubtractionTokenTest {

  @Test
  public void testOperator() {
    assertTrue(new SubtractionToken().getOperator() instanceof SubtractionOperator);
  }

}