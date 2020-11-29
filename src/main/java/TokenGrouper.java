import java.util.ArrayList;
import java.util.List;

/**
 * Groups tokens together into atomic execution blocks. The entire group must be processed before
 * the next group can be processed.
 */
public class TokenGrouper {

  /**
   * Groups tokens into atomic execution blocks (i.e. {@link TokenGroup}s).
   * @param tokens The tokens to group.
   * @return The groups.
   */
  public List<TokenGroup> group(List<Token> tokens) {

    // Holds all different groups
    List<TokenGroup> groups = new ArrayList<>();

    // Holds the tokens in the current group
    TokenGroup currentGroup = new TokenGroup();

    // Loop through all of the tokens
    for (Token token : tokens) {
      if (token instanceof WhitespaceToken || token instanceof DisplayStackToken) {
        // End of group

        if (!currentGroup.isEmpty()) {
          // Group is not empty - add the group to the groups list
          groups.add(currentGroup);
          // Start a new group. Not required if the group is empty anyway
          currentGroup = new TokenGroup();
        }

        if (token instanceof DisplayStackToken) {
          // A display token is in a group of its own
          TokenGroup displayTokenGroup = new TokenGroup();
          displayTokenGroup.addAndTidy(token);
          groups.add(displayTokenGroup);
        }
      } else {
        // The token does not split groups so just add it to the current group
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
