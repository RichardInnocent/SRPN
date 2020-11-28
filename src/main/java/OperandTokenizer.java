/**
 * Tokenizes operands. These are always inputted as integers.
 */
public class OperandTokenizer extends AbstractTokenizer {

  @Override
  protected ExecutionState attemptTokenizationAndThrowExceptions(
      TokenizationResultBuilder resultBuilder) {
    // Map to character array so it's easy to iterate
    char[] unprocessedChars = resultBuilder.getUnprocessedCommand().toCharArray();

    StringBuilder operandBuilder = new StringBuilder();
    for (char character : unprocessedChars) {
      if (Character.isDigit(character)) {
        // Char is a number - add it to the number string
        operandBuilder.append(character);
      } else {
        // This is not a number so cannot be parsed as an operand. Stop tokenization.
        break;
      }
    }

    // Build the result
    String operandText = operandBuilder.toString();

    // If the result is empty, there were no numbers so we were unable to tokenize anything here
    if (operandText.isEmpty()) {
      return ExecutionState.FAILED;
    }

    try {
      // Build the operand token and add it to result
      resultBuilder.addToken(OperandToken.forValue(operandText, determineRadix(operandText)));
    } catch (NumberFormatException e) {
      /* Number format exception thrown probably because the radix is octal but the user has
       * included invalid characters (e.g. 8 or 9). In this case, silently consume the exception and
       * don't add any tokens to the stack. */
    }

    // Increment the index so that it moves to the character after the end of the numeric sequence
    resultBuilder.incrementCurrentIndexBy(operandText.length());
    return ExecutionState.SUCCEEDED;
  }

  /**
   * A number will always be interpreted in base 10 unless both of the following are true:
   * <ul>
   *   <li>The number starts with {@code 0}.</li>
   *   <li>The number immediately after the 0 is a valid number in base 8, i.e. the number <i>x</i>
   *   is in the range {@code 0 <= x <= 7}.</li>
   * </ul>
   */
  private int determineRadix(String value) {
    if (!value.startsWith("0") || value.length() < 2) {
      // Number doesn't start with 0 so it's definitely in base 10
      return 10;
    }
    // If the character after the 0 is > 7, continue in base 10. Otherwise, use base 8.
    char nextChar = value.charAt(1);
    return nextChar == '8' || nextChar == '9' ? 10 : 8;
  }

}
