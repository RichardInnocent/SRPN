import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Instead of splitting commands into a set of characters, it would be much more useful to split
 * them down into a list of tokens - each token controlling its own state and effect. This should
 * make the management of tokens much easier, and saves us having to implement a big if statement
 * depending on their type.</p>
 *
 * <p>Therefore, this tokenizer does just that - it takes a command and strips it down to a
 * collection of {@link Token}s.</p>
 */
public class InputLineTokenizer {

  /* The tokenizers that we can use to turn the characters into tokens. This allows relatively easy
   * expansion in future - if there's a new command character that's added, we can just add the
   * tokenizer to this list. */
  private final List<Tokenizer> tokenizers = Arrays.asList(
      // Parses comments. This needs to go first so we can ignore everything in a comment
      new CommentTokenizer(),
      new OperandTokenizer(),       // Parses operands
      new MathOperatorTokenizer(),  // Parses maths operators, e.g. +-/*%^
      new RandomNumberTokenizer(),  // Parses random numbers, i.e. 'r'
      new DisplayStackTokenizer(),   // Parses the display stack command, i.e. 'd'
      new WhitespaceTokenizer(),    // Parses all whitespace
      new EqualsTokenizer()         // Parses the equals command, i.e. '='
  );

  /**
   * Parses the command and returns a corresponding list of tokens.
   * @param command The command to process.
   * @return A list of tokens that represent the command.
   */
  public List<Token> tokenize(String command) {
    if (command == null || command.isBlank()) {
      // Nothing to process
      return Collections.emptyList();
    }

    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder(command);
    tokenizationLoop: while (!resultBuilder.isComplete()) {
      for (Tokenizer tokenizer : tokenizers) {
        if (tokenizer.attemptTokenization(resultBuilder).succeeded()) {
          continue tokenizationLoop;
        }
      }

      // Couldn't be handled by the tokenizers so must be invalid input
      printInvalidCharacterMessage(
          resultBuilder.getOriginalCommand().charAt(resultBuilder.getCurrentIndex())
      );
      resultBuilder.incrementCurrentIndex();
    }

    return resultBuilder.getTokens();
  }

  private void printInvalidCharacterMessage(char invalidCharacter) {
    if (invalidCharacter > 127) {
      System.out.print("Unrecognised operator or operand \"�\".\n".repeat(2));
    } else {
      System.out.printf("Unrecognised operator or operand \"%c\".\n", invalidCharacter);
    }
  }

}
