package jcip.chapter05;

import java.util.concurrent.BlockingQueue;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-10：恢复中断以避免屏蔽中断
 */
public class TaskRunnable implements Runnable {

  BlockingQueue<Task> queue;

  @Override
  public void run() {
    try {
      processTask(queue.take());
    } catch (InterruptedException e) {
      //恢复被中断的状态
      Thread.currentThread().interrupt();
    }
  }

  void processTask(Task task) {
    // Handle the task
  }

  interface Task {

  }
}
