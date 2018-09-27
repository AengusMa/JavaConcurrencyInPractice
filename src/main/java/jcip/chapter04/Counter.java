package jcip.chapter04;

import jcip.annotations.GuardedBy;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单4-1：使用java监视器模式的线程安全计数器
 */
@ThreadSafe
public class Counter {

  @GuardedBy("this")
  private long value = 0;

  public synchronized long getValue() {
    return value;
  }

  public synchronized long increment() {
    if (value == Long.MAX_VALUE) {
      throw new IllegalStateException("counter overflow");
    }
    return ++value;
  }
}
