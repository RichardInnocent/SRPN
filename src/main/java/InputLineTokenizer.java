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

  /* The tokenizers that we can use to turn the characters into tokens. These tokenizers run in
   * order on each potentially-tokenizable part of the command. This is a chain-of-command (esque)
   * pattern where we order tokenizers using a collection rather than a reference to the next link.
   * This allows relatively easy expansion in future - if there's a new command character that's
   * added, we can just add the tokenizer to this list. */
  private final List<Tokenizer> tokenizers = Arrays.asList(
      // Parses comments. This needs to go first so we can ignore everything in a comment
      new CommentTokenizer(),
      new OperandTokenizer(),       // Parses operands
      new MathOperatorTokenizer(),  // Parses maths operators, e.g. +-/*%^
      new RandomNumberTokenizer(),  // Parses random numbers, i.e. 'r'
      new DisplayStackTokenizer(),  // Parses the display stack command, i.e. 'd'
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

    /* The result builder consists of the command and will be updated by the tokenizers as the
     * command is sequentially converted into tokens */
    TokenizationResultBuilder resultBuilder = new TokenizationResultBuilder(command);

    // While we haven't finished processing the command
    tokenizationLoop: while (!resultBuilder.isComplete()) {
      // Loop through each tokenizer until we find one that can handle the next part of the command
      for (Tokenizer tokenizer : tokenizers) {
        // Attempting tokenization will fail if the tokenizer can't handle the next part
        if (tokenizer.attemptTokenization(resultBuilder).succeeded()) {
          // Tokenization was successful so skip to the next part bit of unparsed command
          continue tokenizationLoop;
        }
      }

      // Couldn't be handled by the tokenizers so the next character must be invalid input
      printInvalidCharacterMessage(
          resultBuilder.getOriginalCommand().charAt(resultBuilder.getCurrentIndex())
      );
      resultBuilder.incrementCurrentIndex();
    }

    return resultBuilder.getTokens();
  }

  private void printInvalidCharacterMessage(char invalidCharacter) {
    /* Characters of a size greater than 127 will be printed twice. This is probably a character
     * encoding issue but this should replicate the behaviour close enough. */
    if (invalidCharacter > 127) {
      System.out.print("Unrecognised operator or operand \"�\".\n".repeat(2));
    } else {
      System.out.printf("Unrecognised operator or operand \"%c\".\n", invalidCharacter);
    }
  }

}
