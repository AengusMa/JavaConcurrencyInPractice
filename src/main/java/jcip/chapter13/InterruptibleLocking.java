package jcip.chapter13;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mawenlong
 * @date 2018/10/24
 *
 * 程序清单13-5：可中断的锁获取操作
 */
public class InterruptibleLocking {

  private Lock lock = new ReentrantLock();

  public boolean sendOnSharedLine(String message) throws InterruptedException {
    lock.lockInterruptibly();
    try {
      return cancellableSendOnSharedLine(message);
    } finally {
      lock.unlock();
    }
  }

  private boolean cancellableSendOnSharedLine(String message) throws InterruptedException {
    /* send something */
    return true;
  }
}
