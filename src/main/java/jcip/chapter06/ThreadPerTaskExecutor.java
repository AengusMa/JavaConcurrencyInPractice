package jcip.chapter06;

import java.util.concurrent.Executor;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单6-5：为每个请求启动一个新线程的Executor
 */
public class ThreadPerTaskExecutor implements Executor {

  @Override
  public void execute(Runnable command) {
    new Thread(command).start();
  }
}
