package jcip.chapter12;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

/**
 * @author mawenlong
 * @date 2018/10/23
 *
 * 程序清单12-8：测试TestingThreadFactory的线程工厂类
 */
public class ThreadPoolTest {

  private final TestingThreadFactory threadFactory = new TestingThreadFactory();

  @Test
  public void testPoolExpansion() throws InterruptedException {
    int MAX_SIZE = 10;
    ExecutorService exec = Executors.newFixedThreadPool(MAX_SIZE, threadFactory);

    for (int i = 0; i < 10 * MAX_SIZE; i++) {
      exec.execute(new Runnable() {
        @Override
        public void run() {
          try {
            Thread.sleep(Long.MAX_VALUE);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      });
    }
    for (int i = 0; i < 20 && threadFactory.numCreated.get() < MAX_SIZE; i++) {
      Thread.sleep(100);
    }
    System.out.println(threadFactory.numCreated.get());
    assertEquals(threadFactory.numCreated.get(), MAX_SIZE);
    exec.shutdownNow();

  }

  class TestingThreadFactory implements ThreadFactory {

    /**
     * 线程工厂的全局统计信息
     */
    public final AtomicInteger numCreated = new AtomicInteger();
    private final ThreadFactory factory = Executors.defaultThreadFactory();

    @Override
    public Thread newThread(Runnable r) {
      numCreated.incrementAndGet();
      return factory.newThread(r);
    }
  }
}
