package jcip.chapter06;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单6-16：在指定时间获取广告
 */
public class RenderWithTimeBudget {

  private static final Ad DEFAULT_AD = new Ad();
  private static final long TIME_BUDGET = 1000;
  private static final ExecutorService exec = Executors.newCachedThreadPool();

  Page renderPageWithAd() throws InterruptedException {
    long endNanos = System.nanoTime() + TIME_BUDGET;
    Future<Ad> f = exec.submit(new FetchAdTask());
//    在等待广告的同时显示页面
    Page page = renderPageBody();
    Ad ad;
    try {
//      只等待指定的时间长度
      long timeLeft = endNanos - System.nanoTime();
      ad = f.get(timeLeft, NANOSECONDS);
    } catch (ExecutionException e) {
      ad = DEFAULT_AD;
    } catch (TimeoutException e) {
      ad = DEFAULT_AD;
      f.cancel(true);
    }
    page.setAd(ad);
    return page;
  }

  Page renderPageBody() {
    return new Page();
  }


  static class Ad {

  }

  static class Page {

    public void setAd(Ad ad) {
    }
  }

  static class FetchAdTask implements Callable<Ad> {

    public Ad call() {
      return new Ad();
    }
  }
}
