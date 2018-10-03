package jcip.chapter07;

/**
 * @author mawenlong
 * @date 2018/10/03
 */
public class PrintTask implements Runnable {

  @Override
  public void run() {
    while (!Thread.interrupted()) {
      System.out.println(String.format("[%s] task running ....", System.currentTimeMillis()));
    }
  }
}
