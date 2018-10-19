package jcip.chapter11;

import java.security.cert.TrustAnchor;
import java.util.concurrent.BlockingQueue;

/**
 * @author mawenlong
 * @date 2018/10/19
 *
 * 程序清单11-1：对任务队列的串行访问
 */
public class WorkerThread extends Thread {

  private final BlockingQueue<Runnable> queue;

  public WorkerThread(BlockingQueue<Runnable> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    while(true){
      try {
        Runnable task = queue.take();
        task.run();
      }catch (InterruptedException e){
//        允许程序退出
        break;
      }
    }
  }
}
