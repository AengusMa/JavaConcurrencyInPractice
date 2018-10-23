package jcip.chapter12;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mawenlong
 * @date 2018/10/23
 *
 * 程序清单12-5：测试BoundedBuffer的生产者-消费者程序
 */
public class PutTakeTest {

  protected static final ExecutorService pool = Executors.newCachedThreadPool();
  protected CyclicBarrier barrier;
  protected final SemaphoreBoundedBuffer<Integer> bb;
  protected final int nTrials, nPairs;
  protected final AtomicInteger putSum = new AtomicInteger(0);
  protected final AtomicInteger takeSum = new AtomicInteger(0);

  public static void main(String[] args) throws Exception {
    new PutTakeTest(10, 10, 100000).test();
    pool.shutdown();
  }

  public PutTakeTest(int capacity, int npairs, int ntrials) {
    this.bb = new SemaphoreBoundedBuffer<Integer>(capacity);
    this.nTrials = ntrials;
    this.nPairs = npairs;
    this.barrier = new CyclicBarrier(npairs * 2 + 1);
  }

  void test() {
    try {
      for (int i = 0; i < nPairs; i++) {
        pool.execute(new Producer());
        pool.execute(new Consumer());
      }
//      等待所有的线程就绪
      barrier.await();
//      等待所有的线程执行完成
      barrier.await();
      System.out.println(putSum.get() == takeSum.get());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 程序清单12-4：适合在测试中使用的随机数生成器
   */
  static int xorShift(int y) {
    y ^= (y << 6);
    y ^= (y >>> 21);
    y ^= (y << 7);
    return y;
  }

  /**
   * 程序清单12-6：生产者
   */
  class Producer implements Runnable {

    @Override
    public void run() {
      try {
        int seed = (this.hashCode() ^ (int) System.nanoTime());
        int sum = 0;
        barrier.await();
        for (int i = nTrials; i > 0; --i) {
          bb.put(seed);
          sum += seed;
          seed = xorShift(seed);
        }
        putSum.getAndAdd(sum);
        /**
         * 生产者完成工作后， invoke await on this barrier
         */
        barrier.await();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }


  }


  /**
   * 程序清单12-6：消费者
   */
  class Consumer implements Runnable {

    @Override
    public void run() {
      try {
        barrier.await();
        int sum = 0;
        for (int i = nTrials; i > 0; --i) {
          sum += bb.take();
        }
        takeSum.getAndAdd(sum);
        /**
         * 消费者完成工作后， invoke await on this barrier
         */
        barrier.await();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
