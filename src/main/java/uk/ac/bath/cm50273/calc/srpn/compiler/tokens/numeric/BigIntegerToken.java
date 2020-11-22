package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric;

import java.math.BigInteger;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric.DummySegmentationFaultException;

public class BigIntegerToken extends IntegerToken {

  private static final BigInteger MINUS_ONE = BigInteger.valueOf(-1L);
  private static final BigInteger UPPER_BOUND = BigInteger.valueOf(Integer.MAX_VALUE);
  private static final BigInteger LOWER_BOUND = BigInteger.valueOf(Integer.MIN_VALUE);

  // We have to keep the value as a BigInteger for now as it could be > Integer.MAX_VALUE or
  // < Integer.MIN_VALUE. When tokenizing, we can ignore this complexity until we get to later
  private final BigInteger value;

  private BigIntegerToken(String valueAsText) throws NumberFormatException {
    if (valueAsText.length() > 120) {
      // Mirror the behaviour of the calculator
      throw new DummySegmentationFaultException();
    }
    value = new BigInteger(valueAsText);
  }

  @Override
  protected int truncatedIntValue() {
    if (value.compareTo(UPPER_BOUND) > 0) {
      return UPPER_BOUND.intValue();
    }
    if (value.compareTo(LOWER_BOUND) < 0) {
      return LOWER_BOUND.intValue();
    }
    return value.intValue();
  }

  /**
   * Creates a new BigIntegerToken with an absolute value equal to this one, with the sign flipped.
   * @return A new BigIntegerToken with the sign flipped.
   * @throws DummySegmentationFaultException Thrown if a minus sign is added and this makes the
   * value longer than 120 characters. This mirrors the behaviour of the calculator.
   */
  @Override
  public IntegerToken flipSign() throws DummySegmentationFaultException {
    return new BigIntegerToken(value.multiply(MINUS_ONE).toString());
  }

  /**
   * Transforms the given text into an integer token.
   * @param value The value to transform into an integer token.
   * @return The integer token.
   * @throws NumberFormatException Thrown if the value is not a number.
   * @throws DummySegmentationFaultException The calculator being emulated throws a segmentation
   * fault if the number being parsed is longer than 120 characters. This exception mirrors this
   * behaviour.
   */
  public static BigIntegerToken forValue(String value)
      throws NumberFormatException, DummySegmentationFaultException {
    // If less than 120 characters in length, we know it will fit in a BigInteger so this if safe
    return new BigIntegerToken(value);
  }

  @Override
  public String getReadableValue() {
    return value.toString();
  }
}
