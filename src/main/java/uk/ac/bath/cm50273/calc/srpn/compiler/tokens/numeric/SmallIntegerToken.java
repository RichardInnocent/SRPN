package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.numeric;

public class SmallIntegerToken extends IntegerToken {

  private final int value;

  private SmallIntegerToken(int value) {
    this.value = value;
  }

  @Override
  protected int truncatedIntValue() {
    return value;
  }

  @Override
  public IntegerToken flipSign() {
    if (value == Integer.MIN_VALUE) {
      // Remove the sign
      return BigIntegerToken.forValue(Integer.toString(value).substring(1));
    }
    return new SmallIntegerToken(-value);
  }

  public static SmallIntegerToken forValue(int value) {
    return new SmallIntegerToken(value);
  }

  public static SmallIntegerToken forValue(String value) {
    return forValue(Integer.parseInt(value));
  }

  @Override
  public String getReadableValue() {
    return Integer.toString(value);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    // If the other object is a small integer, we can be a bit more efficient and compare the int
    // value itself. Otherwise, we'll have to use the parent method which allows the comparison
    // between various types of IntegerToken
    return other instanceof SmallIntegerToken ?
        this.value == ((SmallIntegerToken) other).value :
        super.equals(other);
  }

}
