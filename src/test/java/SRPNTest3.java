import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SRPNTest3 extends SystemOutTest {

  private final SRPN srpn = new SRPN();

  @Test
  public void testCase1() {
    srpn.processCommand("2147483647");
    srpn.processCommand("1");
    srpn.processCommand("+");
    srpn.processCommand("=");
    assertEquals("2147483647", getLatestPrintedContent());
  }

  @Test
  public void testCase2() {
    srpn.processCommand("-2147483647");
    srpn.processCommand("1");
    srpn.processCommand("-");
    srpn.processCommand("=");
    assertEquals("-2147483648", getLatestPrintedContent());
    srpn.processCommand("20");
    srpn.processCommand("-");
    srpn.processCommand("=");
    assertEquals("-2147483648", getLatestPrintedContent());
  }

  @Test
  public void testCase3() {
    srpn.processCommand("100000");
    srpn.processCommand("0");
    srpn.processCommand("-");
    srpn.processCommand("d");
    assertEquals("100000", getLatestPrintedContent());
    srpn.processCommand("*");
    assertEquals("Stack underflow.", getLatestPrintedContent());
    srpn.processCommand("=");
    assertEquals("100000", getLatestPrintedContent());
  }

}
