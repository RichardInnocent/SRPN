package uk.ac.bath.cm50273.calc.srpn;

import java.util.List;
import uk.ac.bath.cm50273.calc.srpn.compiler.InputLineTokenizer;
import uk.ac.bath.cm50273.calc.srpn.compiler.TokenGrouper;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.Token;
import uk.ac.bath.cm50273.calc.srpn.compiler.tokens.TokenGroup;
import uk.ac.bath.cm50273.calc.srpn.execution.TokenGroupExecutor;

public class SRPN {

  public static final int STACK_SIZE = 23;

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
