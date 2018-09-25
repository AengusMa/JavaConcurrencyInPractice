package jcip.chapter02;

import java.math.BigInteger;
import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import jcip.annotations.GuardedBy;
import jcip.annotations.ThreadSafe;

/**
 * @author mawenlong
 * @date 2018/09/25
 *
 * 程序清单2-8：缓存最近执行因数分解的数值及其计算结果的Servlet。减小同步代码块，提高性能
 */
@ThreadSafe
public class CachedFactorizer extends GenericServlet implements Servlet {

  @GuardedBy("this")
  private BigInteger lastNumber;
  @GuardedBy("this")
  private BigInteger[] lastFactors;
  /**
   * 访问的次数
   */
  @GuardedBy("this")
  private long hits;
  /**
   * 缓存的命中次数
   */
  @GuardedBy("this")
  private long cacheHits;

  public synchronized long getHits() {
    return hits;
  }

  /**
   * 缓存的命中比率
   */
  public synchronized double getCacheHitRatio() {
    return (double) cacheHits / (double) hits;
  }

  @Override
  public void service(ServletRequest req, ServletResponse res) {
    BigInteger i = extractFromRequest(req);
    BigInteger[] factors = null;
    synchronized (this) {
      ++hits;
      if (i.equals(lastNumber)) {
        ++cacheHits;
        factors = lastFactors.clone();
      }
    }
    if (factors == null) {
      factors = factor(i);
      synchronized (this) {
        lastNumber = i;
        lastFactors = factors.clone();
      }
    }
    encodeIntoResponse(res, factors);
  }

  void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
  }

  BigInteger extractFromRequest(ServletRequest req) {
    return new BigInteger("7");
  }

  BigInteger[] factor(BigInteger i) {
    // Doesn't really factor
    return new BigInteger[]{i};
  }
}
