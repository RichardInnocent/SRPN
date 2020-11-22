package uk.ac.bath.cm50273.calc.srpn;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SRPNTest1 extends SystemOutTest {

  private final SRPN srpn = new SRPN();

  @Test
  public void testCase1() {
    srpn.processCommand("10");
    srpn.processCommand("2");
    srpn.processCommand("+");
    srpn.processCommand("=");
    assertEquals("12", getLatestPrintedContent());
  }

  @Test
  public void testCase2() {
    srpn.processCommand("11");
    srpn.processCommand("3");
    srpn.processCommand("-");
    srpn.processCommand("=");
    assertEquals("8", getLatestPrintedContent());
  }

  @Test
  public void testCase3() {
    srpn.processCommand("9");
    srpn.processCommand("4");
    srpn.processCommand("*");
    srpn.processCommand("=");
    assertEquals("36", getLatestPrintedContent());
  }

  @Test
  public void testCase4() {
    srpn.processCommand("11");
    srpn.processCommand("3");
    srpn.processCommand("/");
    srpn.processCommand("=");
    assertEquals("3", getLatestPrintedContent());
  }

  @Test
  public void testCase5() {
    srpn.processCommand("11");
    srpn.processCommand("3");
    srpn.processCommand("%");
    srpn.processCommand("=");
    assertEquals("2", getLatestPrintedContent());
  }

}
