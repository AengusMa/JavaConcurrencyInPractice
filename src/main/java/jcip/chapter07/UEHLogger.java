package jcip.chapter07;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mawenlong
 * @date 2018/09/30
 *
 * 程序清单7-25：将异常写入日志的UncaughtExceptionHandler
 */
public class UEHLogger  implements Thread.UncaughtExceptionHandler{

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    Logger logger = Logger.getAnonymousLogger();
    logger.log(Level.SEVERE, "Thread terminated with exception: " + t.getName(), e);
  }

  public static void main(String[] args) {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        throw new IllegalArgumentException("fake");
      }
    });

    thread.setUncaughtExceptionHandler(new UEHLogger());
    thread.start();

    try {
      Thread.sleep(3000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
