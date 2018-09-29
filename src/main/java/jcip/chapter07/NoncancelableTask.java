package jcip.chapter07;

import java.util.concurrent.BlockingQueue;
import javafx.concurrent.Task;

/**
 * @author mawenlong
 * @date 2018/09/29
 *
 * 程序清单7-7：不可取消的任务在退出前恢复中断
 */
public class NoncancelableTask {

  public Task getNextTask(BlockingQueue<Task> queue) {
    boolean interrupted = false;
    try {
      while (true) {
        try {
          return queue.take();
        } catch (InterruptedException e) {
          interrupted = true;
//        重新尝试
        }
      }
    } finally {
      if (interrupted) {
        Thread.currentThread().isInterrupted();
      }
    }
  }
}
