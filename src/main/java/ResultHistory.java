import java.util.Arrays;

public class ResultHistory {

  private final int[] results = new int[23];
  private int resultIndex = 0;

  public void append(int result) throws ArrayIndexOutOfBoundsException {
    results[resultIndex++] = result;
  }

  public int[] getResults() {
    return Arrays.copyOf(results, resultIndex+1);
  }

}
