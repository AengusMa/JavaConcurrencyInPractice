package jcip.chapter12;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author mawenlong
 * @date 2018/10/23
 */
public class BoundedBufferTest {

  private static final long LOCKUP_DETECT_TIMEOUT = 5000;
  private static final int CAPACITY = 10000;
  private static final int THRESHOLD = 10000;

  /**
   * 程序清单12-2：BoundedBuffer的基本单元测试
   */
  @Test
  public void testIsEmptyWhenConstructed() {
    SemaphoreBoundedBuffer<Integer> bb = new SemaphoreBoundedBuffer<Integer>(10);
    assertTrue(bb.isEmpty());
    assertFalse(bb.isFull());
  }

  @Test
  public void testIsFullAfterPuts() throws InterruptedException {
    SemaphoreBoundedBuffer<Integer> bb = new SemaphoreBoundedBuffer<Integer>(10);
    for (int i = 0; i < 10; i++) {
      bb.put(i);
    }
    assertTrue(bb.isFull());
    assertFalse(bb.isEmpty());
  }

  /**
   * 程序清单12-3：测试阻塞行为以及对中断的响应性
   */
  @Test
  public void testTakeBlocksWhenEmpty() {
    final SemaphoreBoundedBuffer<Integer> bb = new SemaphoreBoundedBuffer<Integer>(10);
    Thread taker = new Thread() {
      @Override
      public void run() {
        try {
//          从空缓存获取
          int unused = bb.take();
//          如果执行到这代表获取成功，标识测试失败
          fail();
        } catch (InterruptedException success) {
          System.out.println("测试成功");
        }
      }
    };
    try {
      taker.start();
      Thread.sleep(LOCKUP_DETECT_TIMEOUT);
      taker.interrupt();
      taker.join(LOCKUP_DETECT_TIMEOUT);
      /**
       * taker线程此刻应该死了
       */
      assertFalse(taker.isAlive());
    } catch (Exception unexpected) {
      fail();
    }
  }

  class Big {

    double[] data = new double[100000];
  }

  /**
   * 程序清单12-7：测试资源泄露
   */
  @Test
  public void testLeak() throws InterruptedException {
    SemaphoreBoundedBuffer<Big> bb = new SemaphoreBoundedBuffer<Big>(CAPACITY);
    int heapSize1 = snapshotHeap();
    for (int i = 0; i < CAPACITY; i++) {
      bb.put(new Big());
    }
    for (int i = 0; i < CAPACITY; i++) {
      bb.take();
    }
    int heapSize2 = snapshotHeap();
    assertTrue(Math.abs(heapSize1 - heapSize2) < THRESHOLD);
  }

  private int snapshotHeap() {
    /* Snapshot heap and return heap size */
    return 0;
  }
}
