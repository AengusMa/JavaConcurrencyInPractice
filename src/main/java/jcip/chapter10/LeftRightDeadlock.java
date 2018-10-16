package jcip.chapter10;

/**
 * @author mawenlong
 * @date 2018/10/16
 *
 * 程序清单10-1：简单的锁顺序死锁（不要这么做）
 */
public class LeftRightDeadlock {

  private final Object left = new Object();
  private final Object right = new Object();

  public void leftRight() {
    synchronized (left) {
      synchronized (right) {
//    dosomething
      }
    }
  }

  public void rightLeft() {
    synchronized (right) {
      synchronized ((left)) {
//    do something
      }
    }
  }
}
