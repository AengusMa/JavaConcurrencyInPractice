package jcip.chapter04;

import jcip.annotations.GuardedBy;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/27
 *
 * 程序清单4-11：线程安全可变的Point类
 */
@ThreadSafe
public class SafePoint {

  @GuardedBy("this")
  private int x, y;

  private SafePoint(int[] a) {
    this(a[0], a[1]);
  }

  public SafePoint(SafePoint p) {
    this(p.get());
  }

  public SafePoint(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public synchronized int[] get() {
    return new int[]{x, y};
  }

  public synchronized void set(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

