package jcip.chapter07;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jcip.annotations.GuardedBy;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/28
 *
 * 程序清单7-1：使用volatile类型的域来保存取消状态
 */
@ThreadSafe
public class PrimeGenerator implements Runnable {

  private static ExecutorService exec = Executors.newCachedThreadPool();
  @GuardedBy("this")
  private final List<BigInteger> primes = new ArrayList<>();
  /**
   * 传递取消状态
   */
  private volatile boolean cancelled;

  @Override
  public void run() {
    BigInteger p = BigInteger.ONE;
    while (!cancelled) {
      p = p.nextProbablePrime();
      synchronized (this) {
        primes.add(p);
      }
    }
  }

  public void cancel() {
    cancelled = true;
  }

  public synchronized List<BigInteger> get() {
    return new ArrayList<>(primes);
  }

  /**
   * 程序清单7-2：一个仅运行一秒钟的素数生成器
   */
  static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
    PrimeGenerator generator = new PrimeGenerator();
    exec.execute(generator);
    try {
      SECONDS.sleep(1);
    } finally {
      generator.cancel();
    }
    return generator.get();
  }

  public static void main(String[] args) throws InterruptedException {
    List<BigInteger> res = aSecondOfPrimes();
    for (int i = 0; i < 10; i++) {
      System.out.println(res.get(i));
    }
    System.out.println(aSecondOfPrimes().size());
  }
}
