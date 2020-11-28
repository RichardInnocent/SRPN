/**
 * <p>While comments shouldn't be converted into tokens (as we don't want to process them), we do
 * need to go through the tokenization process so that we can avoid converting any comment content
 * into tokens.</p>
 * <p>The general idea is that a comment is denoted by a {@code #} symbol, and remains active until
 * the next {@code #} is detected. However, a {@code #} is only interpreted as a valid comment
 * toggle if both of the following are met:</p>
 * <ul>
 *   <li>The {@code #} is at the start of the line, or is preceded by whitespace.</li>
 *   <li>The {@code #} is at the end of the line, or is proceeded by whitespace.</li>
 * </ul>
 * <p>When a comment is encountered, all subsequent characters will be excluded from further
 * tokenization. This includes everything from the first {@code #} to (and including) the closing
 * {@code #} and subsequent whitespace character (if present). Consequently, if a whitespace exists
 * prior to the opening {@code #}, it will <i>not</i> form part of the comment. This is by design,
 * and mean that command grouping is preserved. For example:
 * <pre><code>1 1 # comment # +</code></pre>
 * would form the tokens
 * <pre><code>[1] [whitespace] [1] [whitespace] [+]</code></pre></p>
 * <p>Comments can span multiple command groups and even entire lines. As such, this tokenizer must
 * keep track of its current state across all tokenization attempts.</p>
 */
public class CommentTokenizer extends AbstractTokenizer {

  /**
   * Is this tokenizer currently processing a comment? As a comment can span multiple lines, we
   * need to keep track of the current state.
   */
  private boolean parsingComment = false;

  @Override
  protected ExecutionState attemptTokenizationAndThrowExceptions(
      TokenizationResultBuilder resultBuilder) {
    String originalCommand = resultBuilder.getOriginalCommand();
    int currentIndex = resultBuilder.getCurrentIndex();

    // While not at the end of the command
    while (currentIndex < originalCommand.length()) {

      // Check if the current character is a # and meets the criteria to be valid
      if (isValidCommentCharacter(originalCommand, currentIndex)) {

        // It's a valid comment character so toggle comment parsing
        parsingComment = !parsingComment;

        // If not at the end, skip the whitespace character that we know follows the #
        currentIndex += isAtEnd(originalCommand, currentIndex) ? 1 : 2;

        // Move on to the next character or end of the command
        continue;
      }

      if (!parsingComment) {
        // No longer parsing a comment - stop consuming command characters
        break;
      } else {
        // Currently processing a comment so discard the command character and move on
        currentIndex++;
      }
    }

    // If the index didn't increase then we didn't process a comment
    if (currentIndex == resultBuilder.getCurrentIndex()) {
      return ExecutionState.FAILED;
    }

    // No need to add a token here as comments should not be interpreted
    resultBuilder.setCurrentIndex(currentIndex);
    return ExecutionState.SUCCEEDED;
  }

  /**
   * Is the current character a {@code #} and is it in a valid position?
   */
  private boolean isValidCommentCharacter(String originalCommand, int index) {
    char currentChar = originalCommand.charAt(index);
    return currentChar == '#' && isInValidPosition(originalCommand, index);
  }

  /**
   * The {@code #} is only in a valid position if both:
   * <ul>
   *   <li>the {@code #} is at the start of the line, or is preceded by whitespace.</li>
   *   <li>the {@code #} is at the end of the line, or is proceeded by whitespace.</li>
   * </ul>
   */
  private boolean isInValidPosition(String originalCommand, int index) {
    return isAtStartOrAfterWhitespace(originalCommand, index)
        && isAtEndOrBeforeWhitespace(originalCommand, index);
  }

  private boolean isAtStartOrAfterWhitespace(String originalCommand, int index) {
    return isAtStart(index) || isPrecededByWhitespace(originalCommand, index);
  }

  private boolean isAtStart(int index) {
    return index == 0;
  }

  private boolean isPrecededByWhitespace(String originalCommand, int index) {
    return Character.isWhitespace(originalCommand.charAt(index-1));
  }

  private boolean isAtEndOrBeforeWhitespace(String originalCommand, int index) {
    return isAtEnd(originalCommand, index) || isProceededByWhitespace(originalCommand, index);
  }

  private boolean isAtEnd(String originalCommand, int index) {
    return index == originalCommand.length() - 1;
  }

  private boolean isProceededByWhitespace(String originalCommand, int index) {
    return Character.isWhitespace(originalCommand.charAt(index+1));
  }

}
