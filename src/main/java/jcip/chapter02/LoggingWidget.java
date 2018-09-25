package jcip.chapter02;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单2-7：如果内置锁不是可重入的，那么这段代码将会发生死锁
 */
public class LoggingWidget extends Widget {

  @Override
  public synchronized void doSomething() {
    System.out.println(toString() + ":calling doSomething");
    super.doSomething();
  }
}

class Widget {

  public synchronized void doSomething() {
//    ...
  }
}
