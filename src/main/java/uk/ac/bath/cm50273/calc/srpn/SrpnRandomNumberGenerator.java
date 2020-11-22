package uk.ac.bath.cm50273.calc.srpn;

public class SrpnRandomNumberGenerator {

  private int count = 0;
  private GccRandomNumberGenerator randomNumberGenerator = new GccRandomNumberGenerator();

  public int nextInt() {
    if (count == 22) {
      randomNumberGenerator = new GccRandomNumberGenerator();
    }
    if (count < 23) {
      count++;
    }
    return randomNumberGenerator.nextInt();
  }

}
