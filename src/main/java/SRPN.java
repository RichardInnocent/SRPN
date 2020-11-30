import java.util.List;

/**
 * <p>The SRPN (saturated reverse Polish notation calculator) is responsible for handling commands
 * and generating the appropriate output to display to the user. The SRPN is accessed publicly via
 * the {@link #processCommand(String)} method.</p>
 * <p>The SRPN recognises various different input types, described in the table below.</p>
 * <br />
 * <table>
 *   <tr>
 *     <th>Type</th>
 *     <th>Symbol/example</th>
 *     <th>Result</th>
 *   </tr>
 *   <tr>
 *     <td>Operand (base 10)</td>
 *     <td>100</td>
 *     <td>An integer value that can be operated upon (100, in this case).</td>
 *   </tr>
 *   <tr>
 *     <td>Operand (base 8)</td>
 *     <td>010</td>
 *     <td>An integer value that can be operated upon (8 in this case). Numbers starting with a zero
 *     and then being proceeded by any character in the range {@code [0-7]} will be interpreted as
 *     octal. Note that, when displaying this value, the result will always be displayed in base
 *     10.</td>
 *   </tr>
 *   <tr>
 *     <td>Random number</td>
 *     <td>{@code r}</td>
 *     <td>Generates a random number between 0 and {@link Integer#MAX_VALUE} that will then be
 *     treated as an operand.</td>
 *   </tr>
 *   <tr>
 *     <td>Equals</td>
 *     <td>{@code =}</td>
 *     <td>Peeks on the stack, or prints {@code "Stack empty."} if the stack is empty.</td>
 *   </tr>
 *   <tr>
 *     <td>Display stack</td>
 *     <td>{@code d}</td>
 *     <td>Prints the stack from oldest entry to newest, or prints {@link Integer#MIN_VALUE} if the
 *     stack is empty.</td>
 *   </tr>
 *   <tr>
 *     <td>Addition</td>
 *     <td>{@code +}</td>
 *     <td>Adds two operands together.</td>
 *   </tr>
 *   <tr>
 *     <td>Subtraction</td>
 *     <td>{@code -}</td>
 *     <td>Subtracts one operand from another.</td>
 *   </tr>
 *   <tr>
 *     <td>Multiplication</td>
 *     <td>{@code *}</td>
 *     <td>Calculates the product of two operands.</td>
 *   </tr>
 *   <tr>
 *     <td>Division</td>
 *     <td>{@code /}</td>
 *     <td>Divides one operand by another.</td>
 *   </tr>
 *   <tr>
 *     <td>Raise to power</td>
 *     <td>{@code ^}</td>
 *     <td>Raises one operand to an exponent.</td>
 *   </tr>
 *   <tr>
 *     <td>Modulo</td>
 *     <td>%</td>
 *     <td>Calculates the modulus of one operand by another.</td>
 *   </tr>
 * </table>
 * <br />
 * <p>Note that this calculator is saturated - as a result, any operations that would result in a
 * value {@code > Integer.MAX_VALUE} or {@code < Integer.MIN_VALUE} will be returned as
 * {@code Integer.MAX_VALUE} and {@code Integer.MIN_VALUE} respectively.</p>
 * <p>This calculator can be used both in reverse Polish notation, and also using common operator
 * notation. To use reverse Polish notation, the values and operators should be separated by
 * whitespace or new lines. For example:
 * <pre><code>20 10-5 / =</code></pre>
 * would result in the output of {@code 4}.</p>
 * <br /><br />
 * <h2>Compilation and Execution Stages</h2>
 * <p>When a command is received, the SRPN processes this through a number of stages to derive the
 * result.</p>
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

  /**
   * Processes the given command and prints the outputs if applicable.
   * @param command The command to process.
   */
  public void processCommand(String command) {
    if (command == null || command.isBlank()) {
      // Nothing to process
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
