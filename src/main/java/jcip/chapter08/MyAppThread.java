package jcip.chapter08;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mawenlong
 * @date 2018/10/14
 *
 * 程序清单8-7：定制Thread基类
 */
public class MyAppThread extends Thread {

  public static final String DEFAULT_NAME = "MyAppThread";
  private static volatile boolean debugLifecycle = false;
  private static final AtomicInteger created = new AtomicInteger();
  private static final AtomicInteger alive = new AtomicInteger();
  private static final Logger log = Logger.getAnonymousLogger();

  public MyAppThread(Runnable r) {
    this(r, DEFAULT_NAME);
  }

  public MyAppThread(Runnable r, String name) {
    super(r, name + "-" + created.incrementAndGet());
    setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

      @Override
      public void uncaughtException(Thread t, Throwable e) {
        log.log(Level.SEVERE, "UNCAUGTH in thread" + t.getName() + e);
      }
    });
  }

  @Override
  public void run() {
//    复制debug标志以确保一致的值
    boolean debug = debugLifecycle;
    if (debug) {
      log.log(Level.FINE, "Created" + getName());
    }
    try {
      alive.incrementAndGet();
      super.run();
    } finally {
      alive.decrementAndGet();
      if (debug) {
        log.log(Level.FINE, "Exiting" + getName());
      }
    }
  }

  public static int getThreadsCreated() {
    return created.get();
  }

  public static int getThreadsAlive() {
    return alive.get();
  }

  public static boolean getDebug() {
    return debugLifecycle;
  }

  public static void setDebug(boolean b) {
    debugLifecycle = b;
  }
}
