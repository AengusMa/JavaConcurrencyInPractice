package jcip.chapter03;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单3-1：在没有同步的情况下共享变量（不要这么做）。NoVisibility可能持续循环下去，读线程可能永远看不到ready的值。
 * NoVisibility可能输出0读线程看到了ready的值，但是没有看到number的值（指令重排序）。
 */
public class NoVisibility {

  private static boolean ready;
  private static int number;

  private static class ReaderThread extends Thread {

    @Override
    public void run() {
      while (!ready) {
        Thread.yield();
      }
      System.out.println(number);
    }
  }

  public static void main(String[] args) {
    new ReaderThread().start();
    number = 42;
    ready = true;
  }
}
