package jcip.chapter05;

import java.util.concurrent.CountDownLatch;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单5-11：在计时测试中使用CountDownLatch来启动和停止线程（闭锁）。确保所有线程就绪后才开始执行
 */
public class TestHarness {

  public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
//    起始门
    final CountDownLatch startGate = new CountDownLatch(1);
//    结束门
    final CountDownLatch endGate = new CountDownLatch(nThreads);
    for (int i = 0; i < nThreads; i++) {
      Thread t = new Thread() {
        @Override
        public void run() {
          try {
//            等待计数器达到零，如果计数器的值非零则会一直阻塞
            startGate.await();
            try {
              System.out.println("线程[" + this + "]开始执行的时间：" + System.nanoTime());
              task.run();
            } finally {
//              使计数器的值减1
              endGate.countDown();
            }
          } catch (InterruptedException ignored) {
          }
        }
      };
      t.start();
    }
    long start = System.nanoTime();
    startGate.countDown();
    endGate.await();
    long end = System.nanoTime();
    return end - start;
  }

  public static void main(String[] args) {
    try {
      long cost = new TestHarness().timeTasks(5, new SimpleTask());
      System.out.println("总执行的时间：" + cost);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static class SimpleTask implements Runnable {

    @Override
    public void run() {
      System.out.println("task running[" + Thread.currentThread() + "]");
    }
  }
}
