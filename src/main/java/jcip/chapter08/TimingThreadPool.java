package jcip.chapter08;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * @author mawenlong
 * @date 2018/10/14
 *
 * 程序清单8-9：增加了日志和计时等功能的线程池
 */
public class TimingThreadPool extends ThreadPoolExecutor {

  public TimingThreadPool() {
    super(1, 1, 0L, TimeUnit.SECONDS, null);
  }

  /**
   * 每个任务在哪个工作线程中执行不确定，所以把开始时间保存在ThreadLocal
   */
  private final ThreadLocal<Long> startTime = new ThreadLocal<>();
  private Logger log = Logger.getLogger("TimingThreadPool");
  private final AtomicLong numTasks = new AtomicLong();
  private final AtomicLong totalTime = new AtomicLong();

  @Override
  protected void beforeExecute(Thread t, Runnable r) {
    super.beforeExecute(t, r);
    log.fine(String.format("Thread %s: start %s", t, r));
    startTime.set(System.nanoTime());
  }

  @Override
  protected void afterExecute(Runnable r, Throwable t) {
    try {
      long endTime = System.nanoTime();
      long taskTime = endTime - startTime.get();
      numTasks.incrementAndGet();
      totalTime.addAndGet(taskTime);
      log.fine(String.format("Thread %s: end %s, time=%dns",
          t, r, taskTime));
    } finally {
      super.afterExecute(r, t);
    }
  }

  @Override
  protected void terminated() {
    try {
      log.info(String.format("Terminated: avg time=%dns",
          totalTime.get() / numTasks.get()));
    } finally {
      super.terminated();
    }

  }
}
