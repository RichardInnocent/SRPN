import static org.junit.Assert.*;

import org.junit.Test;

public class RandomNumberTokenTest {

  private static final double DELTA = 1e-8;

  private final RandomNumberGenerator randomNumberGenerator = createOrderedNumberGenerator();
  private final RandomNumberToken token = new RandomNumberToken(randomNumberGenerator);

  @Test(expected = NullPointerException.class)
  public void testRandomNumberGeneratorCannotBeNull() {
    new RandomNumberToken(null);
  }

  @Test
  public void testApplyingTokenWhenStackIsFullThrowsExceptionAndDoesNotAdvanceRandomNumberSequence() {
    SizeRestrictedStack<Double> operandStack = new SizeRestrictedStack<>(1);
    operandStack.push(10d);
    try {
      token.apply(operandStack);
      fail("No exception thrown");
    } catch (StackOverflowException e) {
      assertEquals(0, randomNumberGenerator.nextInt()); // This is the first generated int
    }
  }

  @Test
  public void testApplyingToken() {
    SizeRestrictedStack<Double> operandStack = new SizeRestrictedStack<>(3);
    token.apply(operandStack);
    token.apply(operandStack);
    token.apply(operandStack);
    assertEquals(0d, operandStack.get(0), DELTA);
    assertEquals(1d, operandStack.get(1), DELTA);
    assertEquals(2d, operandStack.get(2), DELTA);

    // Check that the sequence advanced
    assertEquals(3d, randomNumberGenerator.nextInt(), DELTA);
  }

  private RandomNumberGenerator createOrderedNumberGenerator() {
    return new RandomNumberGenerator() {
      private int count = 0;
      @Override
      public int nextInt() {
        return count++;
      }
    };
  }

}