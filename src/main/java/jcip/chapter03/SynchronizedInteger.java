package jcip.chapter03;

import jcip.annotations.GuardedBy;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-3：线程安全的可变整数类。get方法也要同步，否则调用get方法仍然会看到失效值
 */
@ThreadSafe
public class SynchronizedInteger {

  @GuardedBy("this")
  private int value;

  public synchronized int getValue() {
    return value;
  }

  public synchronized void setValue(int value) {
    this.value = value;
  }
}
