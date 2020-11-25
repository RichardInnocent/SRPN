import java.util.ArrayList;
import java.util.List;

public class TokenGrouper {

  public List<TokenGroup> group(List<Token> tokens) {
    List<TokenGroup> groups = new ArrayList<>();

    TokenGroup currentGroup = new TokenGroup();
    for (Token token : tokens) {
      if (token instanceof WhitespaceToken) {
        // End of group

        if (!currentGroup.isEmpty()) {
          // Group is not empty - add the group to the groups list
          groups.add(currentGroup);
          // Start a new group
          currentGroup = new TokenGroup();
        }

        // No need to do anything if the group is empty anyway
      } else if (token instanceof DisplayStackToken) {
        // A display token is in a group of its own
        groups.add(currentGroup);

        TokenGroup displayTokenGroup = new TokenGroup();
        displayTokenGroup.addAndTidy(token);
        groups.add(displayTokenGroup);

        currentGroup = new TokenGroup();
      } else {
          currentGroup.addAndTidy(token);
      }
    }

    // Add the remaining group, but only if it actually contains some tokens
    if (!currentGroup.isEmpty()) {
      groups.add(currentGroup);
    }

    return groups;
  }

}
