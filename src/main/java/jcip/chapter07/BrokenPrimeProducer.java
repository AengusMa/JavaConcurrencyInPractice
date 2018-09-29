package jcip.chapter07;


import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单7-3：不可靠的取消操作将把生产者置于阻塞的操作中（不要这么做）
 */
public class BrokenPrimeProducer extends Thread {

  private final BlockingQueue<BigInteger> queue;
  private volatile boolean cancelled = false;

  public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      BigInteger p = BigInteger.ONE;
      while (!cancelled) {
//        队列的put可能会阻塞，从而没有机会检查“取消状态”
        queue.put(p = p.nextProbablePrime());
      }
    } catch (InterruptedException e) {

    }
  }

  public void cancel() {
    cancelled = true;
  }
}
