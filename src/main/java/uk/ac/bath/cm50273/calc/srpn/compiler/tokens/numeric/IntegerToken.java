package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric;

import java.util.Objects;
import uk.ac.bath.cm50273.calc.srpn.CalculatorException;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.AbstractToken;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric.DummySegmentationFaultException;
import uk.ac.bath.cm50273.calc.srpn.execution.SizeRestrictedStack;

/**
 * Represents an integer operand token. As the calculator should not overflow, we have to store the
 * value in its original form, even if this is larger than a primitive {@code int}. We can figure
 * out how to deal with this when computing the values.
 */
public abstract class IntegerToken extends AbstractToken {

  @Override
  public void apply(SizeRestrictedStack<Integer> operandStack) throws CalculatorException {
    operandStack.push(truncatedIntValue());
  }

  /**
   * Gets the long value of the token. This result will be capped at the bounds of an integer, as
   * shown below:
   * <table>
   *   <tr>
   *     <th>Original value</th>
   *     <th>{@code truncatedIntValue}</th>
   *   </tr>
   *   <tr>
   *     <td>{@code 5}</td>
   *     <td>{@code 5}</td>
   *   </tr>
   *   <tr>
   *     <td>{@code -5}</td>
   *     <td>{@code -5}</td>
   *   </tr>
   *   <tr>
   *     <td>{@code Integer.MIN_VALUE-1}</td>
   *     <td>{@code Integer.MIN_VALUE}</td>
   *   </tr>
   *   <tr>
   *     <td>{@code Integer.MAX_VALUE+1}</td>
   *     <td>{@code Integer.MAX_VALUE}</td>
   *   </tr>
   * </table>
   */
  protected abstract int truncatedIntValue();

  /**
   * Flips the sign of the number stored in the token.
   * @return A new integer token with an absolute value equal that this instance, but with the sign
   * flipped.
   */
  public abstract IntegerToken flipSign();

  /**
   * Creates an integer token from the given string.
   * @param value The integer represented as text.
   * @return An integer token.
   * @throws NumberFormatException Thrown if {@code value} is not a valid integer.
   * @throws DummySegmentationFaultException Thrown if {@code value.length() > 120} - this causes
   * the calculator to throw a segmentation fault so we should mirror this behaviour.
   */
  public static IntegerToken forValue(String value)
      throws NumberFormatException, DummySegmentationFaultException {
    try {
      return SmallIntegerToken.forValue(value);
    } catch (NumberFormatException e) {
      // The number might just be too large - try using a bigger token
      return BigIntegerToken.forValue(value);
    }
  }

  /**
   * Creates an integer token from the given int.
   * @param value The value.
   * @return An integer token.
   */
  public static SmallIntegerToken forValue(int value) {
    return SmallIntegerToken.forValue(value);
  }

  @Override
  public int hashCode() {
    return getReadableValue().hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof IntegerToken)) {
      return false;
    }

    IntegerToken that = (IntegerToken) other;

    // Equal if the text representation of the numbers is identical. Therefore, something like
    // BigIntegerToken.forValue("12345").equals(SmallIntegerToken.forValue("12345")
    // will return true, as these tokens should be processed in the same way
    return Objects.equals(getReadableValue(), that.getReadableValue());
  }

}
