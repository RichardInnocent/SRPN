public class CommentTokenizer extends AbstractTokenizer {

  private boolean parsingComment = false;

  @Override
  protected ExecutionState attemptTokenizationAndThrowExceptions(
      TokenizationResultBuilder resultBuilder) {
    String originalCommand = resultBuilder.getOriginalCommand();
    int currentIndex = resultBuilder.getCurrentIndex();

    do {
      if (isValidCommentCharacter(originalCommand, currentIndex)) {
        parsingComment = !parsingComment;
        // If not at the end, skip the whitespace character that we know follows the #
        currentIndex += isAtEnd(originalCommand, currentIndex) ? 1 : 2;
        continue;
      }
      if (!parsingComment) {
        break;
      }
    } while (parsingComment && currentIndex < originalCommand.length());

    // If the index didn't increase then we didn't process a comment
    if (currentIndex == resultBuilder.getCurrentIndex()) {
      return ExecutionState.FAILED;
    }

    // No need to add a token here as comments should not be interpreted
    resultBuilder.setCurrentIndex(currentIndex);
    return ExecutionState.SUCCEEDED;
  }

  private boolean isValidCommentCharacter(String originalCommand, int index) {
    char currentChar = originalCommand.charAt(index);
    return currentChar == '#' && isInValidPosition(originalCommand, index);
  }

  private boolean isInValidPosition(String originalCommand, int index) {
    return isAtStartOrAfterWhitespace(originalCommand, index)
        && isAtEndOrBeforeWhitespace(originalCommand, index);
  }

  private boolean isAtStartOrAfterWhitespace(String originalCommand, int index) {
    return isAtStart(index) || isPrecededByWhitespace(originalCommand, index);
  }

  private boolean isAtEndOrBeforeWhitespace(String originalCommand, int index) {
    return isAtEnd(originalCommand, index) || isProceededByWhitespace(originalCommand, index);
  }

  private boolean isAtStart(int index) {
    return index == 0;
  }

  private boolean isPrecededByWhitespace(String originalCommand, int index) {
    return Character.isWhitespace(originalCommand.charAt(index-1));
  }

  private boolean isAtEnd(String originalCommand, int index) {
    return index == originalCommand.length() - 1;
  }

  private boolean isProceededByWhitespace(String originalCommand, int index) {
    return Character.isWhitespace(originalCommand.charAt(index+1));
  }

}
