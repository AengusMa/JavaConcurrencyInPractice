package jcip.chapter07;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * @author mawenlong
 * @date 2018/09/29
 */
public class PrimeProducer extends Thread {

  private final BlockingQueue<BigInteger> queue;

  public PrimeProducer(BlockingQueue<BigInteger> queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      BigInteger p = BigInteger.ONE;
      while (!Thread.currentThread().isInterrupted()) {
        queue.put(p = p.nextProbablePrime());
      }
    } catch (InterruptedException consumed) {
//      允许线程退出
    }
  }
}
