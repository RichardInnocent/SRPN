public class SmallOperandToken extends OperandToken {

  private final int value;

  private SmallOperandToken(int value) {
    this.value = value;
  }

  @Override
  protected int truncateDoubleToIntBounds() {
    return value;
  }

  @Override
  public OperandToken flipSign() {
    if (value == Integer.MIN_VALUE) {
      // Remove the sign
      return BigOperandToken.forValue(Integer.toString(value).substring(1));
    }
    return new SmallOperandToken(-value);
  }

  public static SmallOperandToken forValue(int value) {
    return new SmallOperandToken(value);
  }

  public static SmallOperandToken forValue(String value) {
    if (value.startsWith("0")) {
      return forValue(Integer.parseInt(value, 8));
    }
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
    return other instanceof SmallOperandToken ?
        this.value == ((SmallOperandToken) other).value :
        super.equals(other);
  }

}
