import java.util.List;

/**
 * <p>The SRPN (saturated reverse Polish notation calculator) is responsible for handling commands
 * and generating the appropriate output to display to the user. The SRPN is accessed publicly via
 * the {@link #processCommand(String)} method. When a command is received, the SRPN processes this
 * through a number of stages, described below.
 * </p>
 * <h2>Compilation and Execution Stages</h2>
 * <h3>1. Tokenization</h3>
 * <p>The tokenization stage takes the raw input string and converts this into a sequence of tokens
 * using an {@link InputLineTokenizer}. For example
 * <pre><code>1 3 +</code></pre>
 * would become
 * <pre><code>[1] [whitespace] [3] [whitespace] [+]</code></pre>
 * </p>
 * <br />
 * <h3>2. Token grouping</h3>
 * <p>Once input has been converted into tokens, these tokens are then split into command groups.
 * Command groups, typically separated by whitespace, are always executed one group at a time, thus
 * the first group must be executed and completed before the second group can be executed. Executing
 * a token group is equivalent to executing an inout line containing only the tokens from that
 * group, i.e.
 * <pre><code>{tokenGroup1} {tokenGroup2}</code></pre>
 * is equivalent to executing two separate commands:
 * <pre><code>{tokenGroup1}<br />{tokenGroup2}</code></pre></p>
 * <p>Tokens are split into groups using a {@link TokenGrouper}.</p>
 * <br />
 * <h3>3. Token group execution</h3>
 * <p>Once the tokens have been grouped, each group needs to be executed. The SRPN uses a stack of
 * operands that can then be operated upon or added to as each token in the group is applied. The
 * tokens include their own instructions regarding how they should affect the stack. The execution
 * is completed using a {@link TokenGroupExecutor}.</p>
 */
public class SRPN {

  /**
   * Converts string commands into a list of tokens.
   */
  private final InputLineTokenizer tokenizer;

  /**
   * Groups tokens.
   */
  private final TokenGrouper tokenGrouper;

  /**
   * Executes the token groups.
   */
  private final TokenGroupExecutor executor;

  /**
   * Creates an SRPN.
   */
  public SRPN() {
    this(new InputLineTokenizer(), new TokenGrouper(), new TokenGroupExecutor());
  }

  /**
   * Creates an SRPN with custom instance fields. These can mocked to improve testability.
   */
  SRPN(InputLineTokenizer tokenizer, TokenGrouper tokenGrouper, TokenGroupExecutor executor) {
    this.tokenGrouper = tokenGrouper;
    this.tokenizer = tokenizer;
    this.executor = executor;
  }

  public void processCommand(String command) {
    if (command == null || command.isBlank()) {
      return;
    }

    // Convert the input into a list of tokens
    List<Token> tokens = tokenizer.tokenize(command);

    // Group the tokens
    List<TokenGroup> tokenGroups = tokenGrouper.group(tokens);

    // Execute the groups
    executor.execute(tokenGroups);
  }

}
