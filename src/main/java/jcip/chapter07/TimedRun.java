package jcip.chapter07;

import static jcip.chapter05.LaunderThrowable.launderThrowable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author mawenlong
 * @date 2018/09/29
 *
 * 程序清单7-10：通过Future来取消任务
 */
public class TimedRun {

  private static final ExecutorService taskExec = Executors.newCachedThreadPool();

  public static void timeRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
    Future<?> task = taskExec.submit(r);
    try {
      task.get(timeout, unit);
    } catch (TimeoutException e) {
//      接下来任务将被取消
    } catch (ExecutionException e) {
//      如果在任务中抛出了异常，那么重新抛出该异常
      throw launderThrowable(e.getCause());
    } finally {
//      如果任务已经结束，那么执行取消操作也不会带来任何影响
//      如果任务正在运行那么将会被中断
      task.cancel(true);
    }

  }
}

