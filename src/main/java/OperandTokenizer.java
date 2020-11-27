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

    resultBuilder.addToken(OperandToken.forValue(operandText));
    resultBuilder.incrementCurrentIndexBy(operandText.length());
    return ExecutionState.SUCCEEDED;
  }

}
