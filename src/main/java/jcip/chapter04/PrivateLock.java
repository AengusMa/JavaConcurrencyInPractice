package jcip.chapter04;

import jcip.annotations.GuardedBy;

/**
 * @author mawenlong
 * @date 2018/09/26
 *
 * 程序清单4-3：通过一个私有锁来保护状态（监视者模式）
 */
public class PrivateLock {

  private final Object myLock = new Object();
  @GuardedBy("myLock")
  Widget widget;

  void someMethod() {
    synchronized (myLock) {
//    访问或修改Widget的状态
    }
  }

  public static class Widget {

  }
}
