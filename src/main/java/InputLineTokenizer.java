import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
  private final List<Tokenizer<?>> tokenizers = Arrays.asList(
      new IntegerTokenizer(),       // Parses operands
      new MathOperatorTokenizer(),  // Parses maths operators, e.g. +-/*%^
      new RandomNumberTokenizer(),  // Parses random numbers, i.e. 'r'
      new DislayStackTokenizer(),   // Parses the display stack command, i.e. 'd'
      new WhitespaceTokenizer(),    // Parses all whitespace
      new EqualsTokenizer()         // Parses the equals command, i.e. '='
  );

  // Are we currently parsing a comment? Comments should not be transformed into tokens
  private boolean parsingComment = false;

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

    List<Token> tokens = new ArrayList<>();

    // All of the command is currently remaining - this is safe as a string is immutable
    String remainingCommand = command;

    // While there is still some of the command that remains untokenized...
    while (!remainingCommand.isEmpty()) {

      char nextChar = remainingCommand.charAt(0);

      if (nextChar == '#') {
        // If the next character is a # then we toggle whether we are currently processing a comment
        parsingComment = !parsingComment;
        // This character has been processed, so we can move on
        remainingCommand = remainingCommand.substring(1);
      } else {
        if (parsingComment) {
          /* If we're currently parsing a comment, just ignore this character - we already know it's
           * not a closing # */
          remainingCommand = remainingCommand.substring(1);
        } else {
          // Try to get the next token
          Optional<TokenizationResult<?>> tokenizationResult = getNextToken(remainingCommand);
          if (tokenizationResult.isPresent()) {
            // We could tokenize it!
            // Add the token to the list of tokens
            tokens.add(tokenizationResult.get().getToken());
            /* Get the command that was left untokenized (a tokenization can span multiple
             * characters so it's not always just the current character that can be excluded) */
            remainingCommand = tokenizationResult.get().getRemainingCommand();
          } else {
            // If we couldn't tokenize the character, move on - we've already printed the
            // unrecognised operand/operator warning
            remainingCommand = remainingCommand.substring(1);
          }
        }
      }
    }

    return tokens;
  }

  private Optional<TokenizationResult<?>> getNextToken(String remainingCommand) {
    Optional<Tokenizer<?>> tokenizer = getTokenizerForNextElement(remainingCommand);
    if (tokenizer.isPresent()) {
      // We found a tokenizer that can tokenize this. Return the result of the tokenization
      return Optional.of(tokenizer.get().tokenize(remainingCommand));
    } else {
      char invalidCharacter = remainingCommand.charAt(0);
      // If a character couldn't be tokenized, it must be invalid.
      // Display a warning and ignore the character
      if (invalidCharacter > 127) {
        System.out.print("Unrecognised operator or operand \"�\".\n".repeat(2));
      } else {
        System.out.printf("Unrecognised operator or operand \"%c\".\n", remainingCommand.charAt(0));
      }
      // Return an empty optional to indicate to the caller that we couldn't tokenize this
      return Optional.empty();
    }
  }

  private Optional<Tokenizer<?>> getTokenizerForNextElement(String remainingCommand) {
    char nextCharacter = remainingCommand.charAt(0);
    // Try to find a tokenizer that can tokenize the next character, or return an empty optional so
    // the caller knows that we can't tokenize this
    return tokenizers.stream()
                     .filter(tokenizer -> tokenizer.canTokenize(nextCharacter))
                     .findAny();
  }

}
