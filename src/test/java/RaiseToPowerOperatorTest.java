import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RaiseToPowerOperatorTest {

  private static final Operator OPERATOR = new RaiseToPowerOperator();

  @Test
  public void test() {

    String acceptableString = "9".repeat(120);
    System.out.println(acceptableString);

    String unacceptableString = "1" + "0".repeat(120);
    System.out.println(unacceptableString);

    // Limited to 120 digits?

    // Crashes at 2^399
  }

  @Test
  public void testRaisingSmallNumbersToPower() {
    int base = 20;
    int exponent = 4;
    assertEquals((int) Math.pow(base, exponent), OPERATOR.apply(base, exponent));
  }

  @Test
  public void testRaisingLargeNumberToZeroToAnyPowerReturnsZero() {
    assertEquals(0, OPERATOR.apply(0, 20));
    assertEquals(0, OPERATOR.apply(0, Integer.MAX_VALUE));
    assertEquals(0, OPERATOR.apply(0, Integer.MIN_VALUE));
  }

  @Test
  public void testRaisingOneToAnyPowerReturnsOne() {
    assertEquals(1, OPERATOR.apply(1, 20));
    assertEquals(1, OPERATOR.apply(1, Integer.MAX_VALUE));
    assertEquals(1, OPERATOR.apply(1, Integer.MIN_VALUE));
  }

  @Test
  public void testRaisingNumberWithinBoundsToPowerOfOneReturnsNumber() {
    assertEquals(20, OPERATOR.apply(20, 1));
    assertEquals(-20, OPERATOR.apply(-20, 1));
  }

  @Test
  public void testRaisingToPowerOf2() {
    assertEquals(9, OPERATOR.apply(3, 2));
    assertEquals(Integer.MAX_VALUE, OPERATOR.apply(Integer.MAX_VALUE, 2));
    assertEquals(Integer.MAX_VALUE, OPERATOR.apply(Integer.MIN_VALUE, 2));
  }

  @Test
  public void testRaisingToPowerOfOrGreaterThan31ReturnsTruncatedValue() {
    assertEquals(Integer.MAX_VALUE, OPERATOR.apply(2, 31));
    assertEquals(Integer.MIN_VALUE, OPERATOR.apply(-2, 31));
    assertEquals(Integer.MAX_VALUE, OPERATOR.apply(2, 32));
    assertEquals(Integer.MAX_VALUE, OPERATOR.apply(-2, 32));
    assertEquals(Integer.MAX_VALUE, OPERATOR.apply(-76, 10356));
    assertEquals(Integer.MIN_VALUE, OPERATOR.apply(-76, 10355));
  }

  @Test
  public void testRaisingToPowerLessThan31ReturnsTruncatedValue() {
    assertEquals((int) Math.pow(-3d, 15d), OPERATOR.apply(-3, 15));
    assertEquals(Integer.MAX_VALUE, OPERATOR.apply(Integer.MAX_VALUE, 30));
    assertEquals(Integer.MAX_VALUE, OPERATOR.apply(Integer.MIN_VALUE, 30));
    assertEquals(Integer.MIN_VALUE, OPERATOR.apply(Integer.MIN_VALUE, 29));
  }

}