package jcip.chapter06;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单6-9：错误的Timer行为
 */
public class OutOfTime {

  public static void main(String[] args) throws InterruptedException {
    Timer timer = new Timer();
    timer.schedule(new ThrowTask(), 1);
    SECONDS.sleep(1);
    timer.schedule(new ThrowTask(),1);
    SECONDS.sleep(5);
  }

  static class ThrowTask extends TimerTask {

    @Override
    public void run() {
      throw new RuntimeException();
    }
  }
}
