package jcip.chapter08;

import java.util.concurrent.ThreadFactory;

/**
 * @author mawenlong
 * @date 2018/10/14
 *
 * 程序清单8-6:自定义的线程工厂
 */
public class MyThreadFactory implements ThreadFactory {

  private final String poolName;

  public MyThreadFactory(String poolName) {
    this.poolName = poolName;
  }

  @Override
  public Thread newThread(Runnable r) {
    return new MyAppThread(r);
  }
}
