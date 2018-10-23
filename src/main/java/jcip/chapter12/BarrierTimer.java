package jcip.chapter12;

/**
 * @author mawenlong
 * @date 2018/10/23
 *
 * 程序清单12-11：基于栅栏的定时器
 */
public class BarrierTimer implements Runnable {

  private boolean started;
  private long startTime, endTime;

  @Override
  public synchronized void run() {
    long t = System.nanoTime();
    if (!started) {
      started = true;
      startTime = t;
    } else {
      endTime = t;
    }
  }

  public synchronized void clear() {
    started = false;
  }

  public synchronized long getTime() {
    return endTime - startTime;
  }
}
