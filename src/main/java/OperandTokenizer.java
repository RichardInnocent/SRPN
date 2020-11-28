/**
 * Tokenizes numbers.
 */
public class OperandTokenizer extends AbstractTokenizer {

  @Override
  protected ExecutionState attemptTokenizationAndThrowExceptions(
      TokenizationResultBuilder resultBuilder) {
    char[] unprocessedChars = resultBuilder.getUnprocessedCommand().toCharArray();

    StringBuilder operandBuilder = new StringBuilder();
    for (char character : unprocessedChars) {
      if (Character.isDigit(character)) {
        operandBuilder.append(character);
      } else {
        break;
      }
    }

    String operandText = operandBuilder.toString();

    if (operandText.isEmpty()) {
      return ExecutionState.FAILED;
    }

    try {
      resultBuilder.addToken(OperandToken.forValue(operandText, determineRadix(operandText)));
    } catch (NumberFormatException e) {
      /* Number format exception thrown probably because the radix is octal but the user has
       * included invalid characters (e.g. 8 or 9). In this case, silently consume the exception and
       * don't add any tokens to the stack. */
    }
    resultBuilder.incrementCurrentIndexBy(operandText.length());
    return ExecutionState.SUCCEEDED;
  }

  private int determineRadix(String value) {
    if (!value.startsWith("0") || value.length() < 2) {
      return 10;
    }
    char nextChar = value.charAt(1);
    return nextChar == '8' || nextChar == '9' ? 10 : 8;
  }

}
