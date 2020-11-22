package uk.ac.bath.cm50273.calc.srpn.compiler.tokens.tokenizer.numeric;

import java.util.Objects;

public class RandomNumberGenerator {

  private final int[] randomNumbers;
  private int nextRandomIndex;

  private RandomNumberGenerator(int[] randomNumbers) throws NullPointerException {
    this.randomNumbers = Objects.requireNonNull(randomNumbers);
  }

  public synchronized int getNextInt() {
    int nextInt = randomNumbers[nextRandomIndex];
    incrementIndex();
    return nextInt;
  }

  private void incrementIndex() {
    nextRandomIndex++;
    if (nextRandomIndex >= randomNumbers.length) {
      nextRandomIndex = 0;
    }
  }

  public static RandomNumberGenerator createDefault() {
    return createFromNumbers(new int[] {
        1804289383,
        846930886,
        1681692777,
        1714636915,
        1957747793,
        424238335,
        719885386,
        1649760492,
        596516649,
        1189641421,
        1025202362,
        1350490027,
        783368690,
        1102520059,
        2044897763,
        1967513926,
        1365180540,
        1540383426,
        304089172,
        1303455736,
        35005211,
        521595368
    });
  }

  public static RandomNumberGenerator createFromNumbers(int[] randomNumbers)
      throws NullPointerException {
    return new RandomNumberGenerator(randomNumbers);
  }

}
