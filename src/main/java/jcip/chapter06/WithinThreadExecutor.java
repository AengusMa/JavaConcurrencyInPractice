package jcip.chapter06;

import java.util.concurrent.Executor;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单6-6：在调用线程中以同步方式执行所有任务的Executor
 */
public class WithinThreadExecutor implements Executor {

  @Override
  public void execute(Runnable command) {
    command.run();
  }
}
