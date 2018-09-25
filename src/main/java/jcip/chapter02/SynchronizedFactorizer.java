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
 * 程序清单2-6：这个Servlet能正确地缓存最新的计算结果，但并发性缺非常糟糕（不要这么做）
 */
@ThreadSafe
public class SynchronizedFactorizer extends GenericServlet implements Servlet {

  @GuardedBy("this")
  private BigInteger lastNumber;
  @GuardedBy("this")
  private BigInteger[] lastFactors;

  @Override
  public synchronized void service(ServletRequest req, ServletResponse res) {
    BigInteger i = extractFromRequest(req);
    if (i.equals(lastNumber)) {
      encodeIntoResponse(res, lastFactors);
    } else {
      BigInteger[] factors = factor(i);
      lastNumber = i;
      lastFactors = factors;
      encodeIntoResponse(res, factors);
    }
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
