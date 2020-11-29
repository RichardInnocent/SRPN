/**
 * <p>While the random numbers are predictable for the first 44 entries (as the first 22 repeat),
 * this repeating pattern unfortunately does not repeat. However, I noticed that the subsequent
 * numbers were always reproducible. From looking at the format of the runtime errors that were
 * occasionally thrown by the calculator, I'd already deducted that the original program was most
 * likely written in C or C++. From past experience, I know that C's {@code rand()} function is only
 * pseudorandom, and will continue to generate the same random numbers given the same seed. I did
 * some testing in repl.it and pleasantly discovered that the random table was generated using the
 * default seed.</p>
 * <p>Therefore, all I had to do was replicate C's generation of random numbers. After some digging,
 * I found the source code of the GNU C Library - found
 * <a href="https://github.com/lattera/glibc">here</a> (accessed 22/11/2020).</p>
 * <p>rand.c's {@code rand} function calls random.c's {@code __random()} function. This passes a
 * random table (similar to {@code RANDOM_TABLE} below) to random_r.c's {@code __random_r} function.
 * For clarity, the algorithm I replicated is
 * <a href="https://github.com/lattera/glibc/blob/master/stdlib/random_r.c">here</a>. I removed the
 * unnecessary parts of the algorithm (such as the random type) and implemented it in Java.</p>
 */
public class GccRandomNumberGenerator implements RandomNumberGenerator {

  // Equivalent to the randtbl - this is modified by the algorithm
  private final int[] randomTable = {
      -1726662223, 379960547, 1735697613, 1040273694, 1313901226, 1627687941, -179304937,
      -2073333483, 1780058412, -1989503057, -615974602, 344556628, 939512070, -1249116260,
      1507946756, -812545463, 154635395, 1388815473, -1926676823, 525320961, -1009028674, 968117788,
      -123449607, 1284210865, 435012392, -2017506339, -911064859, -370259173, 1132637927,
      1398500161, -205601318
  };

  private int frontIndex = 3; // equivalent to fprt, using an index instead of a pointer
  private int rearIndex = 0; // equivalent to rptr, using an index instead of a pointer

  /**
   * Generates a random integer that mirrors how the GNU library implements the {@code rand()}
   * function.
   * @return A random integer.
   */
  @Override
  public int nextInt() {
    randomTable[frontIndex] += randomTable[rearIndex];
    int returnValue = randomTable[frontIndex] >> 1;

    frontIndex++;
    rearIndex++;
    if (frontIndex >= randomTable.length) {
      /* If the front index now refers to a point beyond the bounds of the randomTable array, set
       * it back to the beginning. */
      frontIndex = 0;
    } else if (rearIndex >= randomTable.length) {
      /* If the rear index now refers to a point beyond the bounds of the randomTable array, set
       * it back to the beginning. We can use an else if here as rearIndex will never be equal to
       * randomTable.length at the same time so this is slightly more efficient. */
      rearIndex = 0;
    }

    /* The C return value is unsigned, while Java's implementation is not. Therefore, if the return
     * value is negative, we have to flip the first bit which indicates the sign. To do this, we
     * can just AND it with a binary number consisting of all 1s except for the first bit. */
    return unsign(returnValue);
  }

  private int unsign(int value) {
    /* The C return value is unsigned, while Java's implementation is not. Therefore, if the return
     * value is negative, we have to flip the first bit which indicates the sign. To do this, we
     * can just AND it with a binary number consisting of all 1s except for the first bit. */
    return value & 0x7fffffff;
  }

}
