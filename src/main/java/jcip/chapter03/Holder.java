package jcip.chapter03;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-15：由于未被正确发布，因此这个类可能出现故障
 */
public class Holder {

  private int n;

  public Holder(int n) {
    this.n = n;
  }

  public void assertSanity() {
    if (n != n) {
      throw new AssertionError("This statement is false.");
    }
  }
}
