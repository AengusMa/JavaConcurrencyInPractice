package jcip.chapter08;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/10/14
 *
 * 程序清单8-4：使用Semaphore 来控制任务的提交速率
 *
 * 使用信号量来控制任务队列的数量，因为线程池使用的默认是无界队列。也可以使用一个计数，或者限制任务队列的长度
 */
@ThreadSafe
public class BoundedExecutor {

  private final Executor exec;
  private final Semaphore semaphore;

  public BoundedExecutor(Executor exec, int bound) {
    this.exec = exec;
    this.semaphore = new Semaphore(bound);
  }

  public void submitTask(final Runnable command) throws InterruptedException {
    semaphore.acquire();
    try {
      exec.execute(new Runnable() {
        @Override
        public void run() {
          try {
            command.run();
          } finally {
            semaphore.release();
          }
        }
      });
    } catch (RejectedExecutionException e) {
      semaphore.release();
    }
  }


}
