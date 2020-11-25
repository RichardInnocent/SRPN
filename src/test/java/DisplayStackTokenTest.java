import static org.junit.Assert.*;

import org.junit.Test;

public class DisplayStackTokenTest {

  @Test
  public void testHashCode() {
    assertEquals("display".hashCode(), new DisplayStackToken().hashCode());
  }

  @Test
  public void testEquals() {
    Token displayStackToken1 = new DisplayStackToken();
    Token displayStackToken2 = new DisplayStackToken();
    Token otherToken = new AdditionToken();

    assertTrue(displayStackToken1.equals(displayStackToken2));
    assertTrue(displayStackToken2.equals(displayStackToken1));
    assertFalse(displayStackToken1.equals(otherToken));
  }

}