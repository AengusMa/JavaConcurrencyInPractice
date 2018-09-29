package jcip.chapter07;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import jcip.annotations.GuardedBy;

/**
 * @author mawenlong
 * @date 2018/09/29
 *
 * 程序清单7-15：向LogWriter添加可靠的取消操作
 */
public class LogService {

  private final BlockingQueue<String> queue;
  private final LoggerThread loggerThread;
  private final PrintWriter writer;
  @GuardedBy("this")
  private boolean isShutdown;
  /**
   * 消息队列的计数，保证消费完
   */
  @GuardedBy("this")
  private int reservations;

  public LogService(Writer writer) {
    this.queue = new LinkedBlockingQueue<String>();
    this.loggerThread = new LoggerThread();
    this.writer = new PrintWriter(writer);
  }

  public void start() {
    loggerThread.start();
  }

  public void stop() {
    synchronized (this) {
      isShutdown = true;
    }
    loggerThread.interrupt();
  }

  public void log(String msg) throws InterruptedException {
    synchronized (this) {
      if (isShutdown) {
        throw new IllegalStateException("Cannot log, log service shutdown!");
      }
      ++reservations;
    }
    queue.put(msg);
  }

  private class LoggerThread extends Thread {

    @Override
    public void run() {
      try {
        while (true) {
          try {
            synchronized (LogService.this) {
              if (isShutdown && reservations == 0) {
                break;
              }
            }
            String msg = queue.take();
            synchronized (LogService.this) {
              --reservations;
            }
            writer.println(msg);
          } catch (InterruptedException e) {
//          retry
          }
        }
      } finally {
        writer.close();
      }
    }
  }
}
