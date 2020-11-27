import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TokenizationResultBuilder {

  private final String command;
  private final List<Token> tokens = new ArrayList<>();

  private int currentIndex = 0;

  public TokenizationResultBuilder(String command) throws NullPointerException {
    this.command = Objects.requireNonNull(command, "Remaining command is null");
  }

  public void addToken(Token token) throws NullPointerException {
    tokens.add(Objects.requireNonNull(token, "Token is null"));
  }

  public void setCurrentIndex(int index) throws IllegalArgumentException {
    if (index < this.currentIndex) {
      throw new IllegalArgumentException(
          String.format(
              "Cannot revert the current index to an earlier position as this could cause a cyclic "
                  + "tokenization process. Current index: %d. Specified index: %d.",
              this.currentIndex, index
          )
      );
    }
    this.currentIndex = index;
  }

  public boolean isComplete() {
    return currentIndex >= command.length();
  }

  public int getCurrentIndex() {
    return currentIndex;
  }

  public void incrementCurrentIndex() {
    currentIndex++;
  }

  public void incrementCurrentIndexBy(int amount) {
    currentIndex += amount;
  }

  public List<Token> getTokens() {
    return new ArrayList<>(tokens);
  }

  public String getOriginalCommand() {
    return command;
  }

  public String getUnprocessedCommand() {
    if (currentIndex == 0) {
      // Not necessary but saves us initialising a redundant string object
      return command;
    }
    if (currentIndex >= command.length()) {
      /* If there's no remaining command, return an empty string. This allows us to use the String
       * pool and avoid StringIndexOutOfBoundsExceptions. */
      return "";
    }
    return command.substring(currentIndex);
  }

}
