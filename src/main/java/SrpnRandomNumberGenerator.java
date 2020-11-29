public class SrpnRandomNumberGenerator implements RandomNumberGenerator {

  private static final SrpnRandomNumberGenerator INSTANCE = new SrpnRandomNumberGenerator();

  private int count = 0;
  private GccRandomNumberGenerator randomNumberGenerator = new GccRandomNumberGenerator();

  private SrpnRandomNumberGenerator() {}

  public static SrpnRandomNumberGenerator getInstance() {
    return INSTANCE;
  }

  @Override
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
