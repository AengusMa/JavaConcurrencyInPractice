package jcip.chapter07;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author mawenlong
 * @date 2018/09/29
 *
 * 程序清单7-8：在外部线程中安排中断（不要这么做）
 */
public class TimedRun1 {

  private static final ScheduledExecutorService cancelService = Executors.newScheduledThreadPool(1);

  public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
    final Thread tashThread = Thread.currentThread();
    cancelService.schedule(new Runnable() {
      @Override
      public void run() {
        tashThread.interrupt();
      }
    }, timeout, unit);
    r.run();
  }
}
