package jcip.chapter07;

import static jcip.chapter05.LaunderThrowable.launderThrowable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author mawenlong
 * @date 2018/09/29
 *
 * 程序清单7-9：在专门的线程中中断任务
 */
public class TimedRun2 {

  private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(1);

  public static void timeRun(final Runnable r, long timeout, TimeUnit unit)
      throws InterruptedException {
    class RethrowableTask implements Runnable {

      private volatile Throwable t;

      @Override
      public void run() {
        try {
          r.run();
        } catch (Throwable t) {
          this.t = t;
        }
      }

      void rethrow() {
        if (t != null) {
          throw launderThrowable(t);
        }
      }
    }
    RethrowableTask task = new RethrowableTask();
    final Thread taskThread = new Thread(task);
    taskThread.start();
    cancelExec.schedule(new Runnable() {
      @Override
      public void run() {
        taskThread.interrupt();
      }
    }, timeout, unit);
    taskThread.join(unit.toMillis(timeout));
    task.rethrow();
  }
}
