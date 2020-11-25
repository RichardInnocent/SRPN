import java.util.List;

public class SRPN {

  private final TokenGrouper tokenGrouper;
  private final InputLineTokenizer tokenizer;
  private final TokenGroupExecutor executor;

  public SRPN() {
    this(new InputLineTokenizer(), new TokenGrouper(), new TokenGroupExecutor());
  }

  SRPN(InputLineTokenizer tokenizer, TokenGrouper tokenGrouper, TokenGroupExecutor executor) {
    this.tokenGrouper = tokenGrouper;
    this.tokenizer = tokenizer;
    this.executor = executor;
  }

  public void processCommand(String command) {
    if (command.isBlank()) {
      return;
    }

    List<Token> tokens = tokenizer.tokenize(command);
    List<TokenGroup> tokenGroups = tokenGrouper.group(tokens);
    executor.execute(tokenGroups);
  }

}
