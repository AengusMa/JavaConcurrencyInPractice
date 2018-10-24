package jcip.chapter12;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;

/**
 * @author mawenlong
 * @date 2018/10/23
 */
public class TimedPutTakeTest extends PutTakeTest {

  private BarrierTimer timer = new BarrierTimer();

  public TimedPutTakeTest(int cap, int pairs, int trials) {
    super(cap, pairs, trials);
    barrier = new CyclicBarrier(nPairs * 2 + 1, timer);
  }

  @Override
  public void test() {
    try {
      timer.clear();
      for (int i = 0; i < nPairs; i++) {
        pool.execute(new PutTakeTest.Producer());
        pool.execute(new PutTakeTest.Consumer());
      }
      barrier.await();
      barrier.await();
      /**
       * 所有任务执行完成之后的时间统计
       */
      long nsPerItem = timer.getTime() / (nPairs * (long) nTrials);
      System.out.print("Throughput: " + nsPerItem + " ns/item");
//            System.out.println(putSum.get() == takeSum.get());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) throws Exception {
//    每个线程中的测试次数
    ConcurrentLinkedQueue<Integer> test = new ConcurrentLinkedQueue<>();
    test.add(1);
    test.add(2);
    test.add(3);
    test.add(4);
    test.poll();
    test.poll();
    int tpt = 100000;
//    for (int cap = 1; cap <= 1000; cap *= 10) {
//      System.out.println("Capacity: " + cap);
//      for (int pairs = 1; pairs <= 128; pairs *= 2) {
//        TimedPutTakeTest t = new TimedPutTakeTest(cap, pairs, tpt);
//        System.out.print("Pairs: " + pairs + "\t");
//        t.test();
//        System.out.print("\t");
//        Thread.sleep(1000);
//        t.test();
//        System.out.println();
//        Thread.sleep(1000);
//      }
//    }
//    PutTakeTest.pool.shutdown();
  }
}
